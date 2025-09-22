import { abort } from "process";
import * as obj from "../../server/obj/obj.ts";

/**
 * @brief - entry point.
 */
function main() {
    console.log("Wavefront obj loader test");

    try {
        const msh = obj.readObj("assets/demon_baby.obj");
        console.log(msh.getName());
    } catch (e) {
        console.log(e);
        process.abort();
    }
}

/**
 * START
 */
main();
