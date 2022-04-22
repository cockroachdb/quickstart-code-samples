package main

import (
	"context"
	"log"
	"os"

	"github.com/jackc/pgx/v4"
)

func execute(conn *pgx.Conn, stmt string) error {
	rows, err := conn.Query(context.Background(), stmt)
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
	return nil
}

func main() {
	// Read in connection string
	connection, err := pgx.Connect(context.Background(), os.Getenv("DATABASE_URL"))
	if err != nil {
		log.Fatal(err)
	}
	defer connection.Close(context.Background())

	statements := [3]string{
		// CREATE the messages table
		"CREATE TABLE IF NOT EXISTS messages (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), message STRING)",
		// INSERT a row into the messages table
		"INSERT INTO messages (message) VALUES ('Hello world!')",
		// SELECT a row from the messages table
		"SELECT message FROM messages"}

	for _, s := range statements {
		execute(connection, s)
	}
}
