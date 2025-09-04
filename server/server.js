import express from "express";
import path from "path";

const app = express();
const port = 8081;

const workpath = process.cwd();

console.log(`Workpath is ${workpath}`);

app.get("/", (req, res) => {
	res.sendFile(path.join(workpath, "tire", "index.html"));
});

app.get("/js/*js", (req, res) => {
	const file = path.join(workpath, "tire", req.url);
	res.sendFile(file);
});

app.get("/shaders/*glsl", (req, res) => {
	const file = path.join(workpath, "tire", req.url);
	res.sendFile(file);
});

app.listen(port, () => {
	console.log(`Example app listening on port ${port}`);
});
