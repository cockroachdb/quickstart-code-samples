import logging
import os
import psycopg
from psycopg.errors import ProgrammingError

def exec_statement(conn, stmt):
    try:
        with conn.cursor() as cur:
            cur.execute(stmt)
            row = cur.fetchone()
            conn.commit()
            if row: print(row[0])
    except ProgrammingError:
        return


def main():

    # Connect to CockroachDB
    try:
        connection = psycopg.connect(os.environ["DATABASE_URL"], application_name="$ docs_quickstart_python")

    except Exception as e:
        logging.fatal("database connection failed")
        logging.fatal(e)
        return

    statements = [
        # Clear out any existing data
        "DROP TABLE IF EXISTS messages",
        # CREATE the messages table
        "CREATE TABLE IF NOT EXISTS messages (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), message STRING)",
        # INSERT a row into the messages table
        "INSERT INTO messages (message) VALUES ('Hello world!')",
        # SELECT a row from the messages table
        "SELECT message FROM messages"
    ]

    for statement in statements:
        exec_statement(connection, statement)

    # Close communication with the database
    connection.close()


if __name__ == "__main__":
    main()
