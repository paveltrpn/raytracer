import { MeshData } from "./mesh_data.ts";
import fs from "node:fs";
import path from "node:path";

/**
 * @brief - read wavefornt obj file in object, contains
 * all mesh related data.
 * @throws {Error} - when file not exist.
 */
export function readObj(filePath: string): MeshData {
    if (!fs.existsSync(filePath)) {
        throw new Error(`File ${filePath} not exist`);
    }

    // Get filename without extension.
    const extension = path.extname(filePath);
    const filename = path.basename(filePath, extension);

    return new MeshData(filename);
}
