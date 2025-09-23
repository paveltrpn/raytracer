

Dependencies:
- - - - - -
Boost
$ apt install libboost-all

Build
- - - - - -
Initialize node modules:
$ node install

Usage
- - - - - - 
Run server:
$ node server/server.ts

Open browser from "http://localhost:8081".

Environment
- - - - - - -
$ npm install -g typescript-language-server typescript
$ npm install -g @biomejs/biome

Tip
- - - - - - 
Don`t forget trigger lint and format
$ npx biome lint tire/.
$ npx biome format tire/. --write
