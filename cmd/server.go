package main

import (
	"fmt"
	"io"
	"log"
	"log/slog"
	"net/http"
	"os"
	"path"
	"path/filepath"
	"time"
)

func handleScript(writer http.ResponseWriter, request *http.Request) {
	slog.Info("request script:", "path", request.URL.Path)

	fp := path.Join("..", "tire", "js", request.URL.Path)

	slog.Info("file:", "path", fp)

	http.ServeFile(writer, request, fp)
}

func handleShader(writer http.ResponseWriter, request *http.Request) {
	slog.Info("request shader:", "path", request.URL.Path)

	fp := path.Join("..", "tire", "js", "shaders", request.URL.Path)

	slog.Info("file:", "path", fp)
	// Open the file
	file, err := os.Open(fp)
	if err != nil {
		if os.IsNotExist(err) {
			http.NotFound(writer, request) // File not found
			return
		}
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}
	defer file.Close()

	// Get file information to set Content-Type and other headers
	fileInfo, err := file.Stat()
	if err != nil {
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	contentType := "text/plain"

	writer.Header().Set("Content-Type", contentType)
	writer.Header().Set("Content-Length", fmt.Sprintf("%d", fileInfo.Size()))
	// Copy the file content to the response writer
	_, err = io.Copy(writer, file)
	if err != nil {
		fmt.Printf("Error copying file: %v\n", err) // Log error, but don't send to client if already writing
	}
}

func handleIndex(writer http.ResponseWriter, request *http.Request) {
	slog.Info("request index:", "path", request.URL.Path)

	fp := path.Join("..", "tire", request.URL.Path, "index.html")

	slog.Info("file:", "path", fp)
	// Open the file
	file, err := os.Open(fp)
	if err != nil {
		if os.IsNotExist(err) {
			http.NotFound(writer, request) // File not found
			return
		}
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}
	defer file.Close()

	// Get file information to set Content-Type and other headers
	fileInfo, err := file.Stat()
	if err != nil {
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	// Set Content-Type header based on file extension (basic example)
	contentType := "application/octet-stream" // Default
	switch filepath.Ext(fp) {
	case ".html", ".htm":
		contentType = "text/html"
	case ".css":
		contentType = "text/css"
	case ".js":
		contentType = "text/javascript"
	case ".png":
		contentType = "image/png"
	case ".jpg", ".jpeg":
		contentType = "image/jpeg"
	case ".gif":
		contentType = "image/gif"
	case ".pdf":
		contentType = "application/pdf"
	}

	writer.Header().Set("Content-Type", contentType)
	writer.Header().Set("Content-Length", fmt.Sprintf("%d", fileInfo.Size()))

	// Copy the file content to the response writer
	_, err = io.Copy(writer, file)
	if err != nil {
		fmt.Printf("Error copying file: %v\n", err) // Log error, but don't send to client if already writing
	}
}

func LoggingMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		start := time.Now()
		log.Printf("Request received: Method=%s, Path=%s, RemoteAddr=%s", r.Method, r.URL.Path, r.RemoteAddr)

		// Call the next handler in the chain
		next.ServeHTTP(w, r)

		duration := time.Since(start)
		log.Printf("Request completed: Method=%s, Path=%s, Duration=%s", r.Method, r.URL.Path, duration)
	})
}

func main() {
	logger := slog.Default()
	slog.SetDefault(logger)

	mux := http.NewServeMux()

	mux.Handle("/", http.HandlerFunc(handleIndex))
	mux.Handle("/js/", http.StripPrefix("/js/", http.HandlerFunc(handleScript)))
	mux.Handle("/shaders/", http.StripPrefix("/shaders/", http.HandlerFunc(handleShader)))

	h := slog.NewTextHandler(os.Stdout, nil)
	loggedMux := slog.NewLogLogger(h, slog.LevelError)

	port := 8081

	server := http.Server{
		Addr:     fmt.Sprintf(":%d", port),
		Handler:  mux,
		ErrorLog: loggedMux,
	}

	fmt.Printf("Server running on http://localhost:%v/\n", port)

	if err := server.ListenAndServe(); err != nil {
		log.Fatal(err)
	}
}
