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
)

func handleScript(writer http.ResponseWriter, request *http.Request) {
	fp := path.Join("..", "tire", "js", request.URL.Path)

	slog.Info("request", "path", request.URL.Path, "file", fp)

	http.ServeFile(writer, request, fp)
}

func handleShader(writer http.ResponseWriter, request *http.Request) {
	fp := path.Join("..", "tire", "shaders", request.URL.Path)

	slog.Info("request", "path", request.URL.Path, "file", fp)

	file, err := os.Open(fp)
	if err != nil {
		if os.IsNotExist(err) {
			http.NotFound(writer, request)
			return
		}
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}
	defer file.Close()

	fileInfo, err := file.Stat()
	if err != nil {
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	contentType := "text/plain"

	writer.Header().Set("Content-Type", contentType)
	writer.Header().Set("Content-Length", fmt.Sprintf("%d", fileInfo.Size()))

	_, err = io.Copy(writer, file)
	if err != nil {
		fmt.Printf("Error copying file: %v\n", err)
	}
}

func handleIndex(writer http.ResponseWriter, request *http.Request) {
	fp := path.Join("..", "tire", request.URL.Path, "index.html")

	slog.Info("request", "path", request.URL.Path, "file", fp)

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

	fileInfo, err := file.Stat()
	if err != nil {
		http.Error(writer, "Internal Server Error", http.StatusInternalServerError)
		return
	}

	// Set Content-Type header based on file extension (basic example)
	contentType := "application/octet-stream"
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

	_, err = io.Copy(writer, file)
	if err != nil {
		fmt.Printf("Error copying file: %v\n", err)
	}
}

func main() {
	// Set default logger.
	logger := slog.Default()
	slog.SetDefault(logger)

	mux := http.NewServeMux()

	mux.Handle("/", http.HandlerFunc(handleIndex))
	mux.Handle("/js/", http.StripPrefix("/js/", http.HandlerFunc(handleScript)))
	mux.Handle("/shaders/", http.StripPrefix("/shaders/", http.HandlerFunc(handleShader)))

	h := slog.NewTextHandler(os.Stdout, nil)
	loggedMux := slog.NewLogLogger(h, slog.LevelError)

	// Server port.
	port := 8081

	// Init server.
	server := http.Server{
		Addr:     fmt.Sprintf(":%d", port),
		Handler:  mux,
		ErrorLog: loggedMux,
	}

	fmt.Printf("Server running on http://localhost:%v/\n", port)

	// Start server.
	if err := server.ListenAndServe(); err != nil {
		log.Fatal(err)
	}
}
