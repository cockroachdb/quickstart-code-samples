package main

import (
	"context"
	"log"
	"os"

	"github.com/jackc/pgx/v4"
)

func main() {
	// Read in connection string
	conn, err := pgx.Connect(context.Background(), os.Getenv("DATABASE_URL"))
	if err != nil {
		log.Fatal(err)
	}
	defer conn.Close(context.Background())
	// Read rows
	rows, err := conn.Query(context.Background(), "SELECT message FROM messages")
	if err != nil {
		log.Fatal(err)
	}
	for rows.Next() {
		var message string
		if err := rows.Scan(&message); err != nil {
			log.Fatal(err)
		}
		log.Printf(message)
	}
}
