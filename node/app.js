const { Client } = require("pg");

(async () => {
    const client = new Client(process.env.DATABASE_URL);

    try {
        // Connect to CockroachDB
        await client.connect();
        // SELECT a row from the messages database
        const result = await client.query("SELECT message FROM messages");
        console.log(result.rows[0].message);
        await client.end();
    } catch (err) {
        console.log(`error connecting: ${err}`);
    }

    // Exit program
    process.exit();
})().catch((err) => console.log(err.stack));