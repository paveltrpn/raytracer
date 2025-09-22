import { MeshData } from "./mesh_data.ts";
import fs from "node:fs";

/**
 * @brief - read wavefornt obj file in object, contains
 * all mesh related data.
 */
export function readObj(path: string): MeshData {
    return new MeshData();
}
