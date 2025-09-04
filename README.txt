

Build
- - - - - -

Dependencies:

Nlohmann json
$ git clone https://github.com/nlohmann/json third_party/json

µWebSockets
$ git clone https://github.com/uNetworking/uWebSockets third_party/µWebSockets

Usage
- - - - - - 

Run server:
$ cd server
$ go run ./cmd/.

Open browser from "http://localhost:8081".

Tip
- - - - - - 
Don`t forget trigger lint and format
$ npx biome lint tire/.
$ npx biome format tire/. --write
