import os
import psycopg2


def main():

    # Connect to CockroachDB
    conn = psycopg2.connect(os.environ['DATABASE_URL'])

    with conn.cursor() as cur:
        # SELECT a row from the messages database
        cur.execute("SELECT message FROM messages")
        rows = cur.fetchall()
        conn.commit()
        for row in rows:
            print(row[0])

    # Close communication with the database
    conn.close()


if __name__ == "__main__":
    main()
