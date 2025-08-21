package main

import (
	"fmt"
	"log"
	"net/http"
	"strings"
)

func handleBasic(writer http.ResponseWriter, request *http.Request) {
	fmt.Printf("handle basic\n")

	var rt []string

	rt = append(rt, "Hello World")

	_, err := writer.Write([]byte(strings.Join(rt, "")))
	if err != nil {
		log.Println(err)
	}
}

func main() {
	mux := http.NewServeMux()
	mux.Handle("/basic", http.HandlerFunc(handleBasic))

	fs := http.FileServer(http.Dir("../app"))
	mux.Handle("/", fs)

	port := 8081

	server := http.Server{
		Addr:    fmt.Sprintf(":%d", port),
		Handler: mux,
	}

	fmt.Printf("Server running on http://localhost:%v/\n", port)
	if err := server.ListenAndServe(); err != nil {
		log.Fatal(err)
	}
}
