import { IArticulo } from "./articulo.interface";
import { IUsuario } from "./usuario.interface";

export interface IComentario {
    id: number;
    texto: string;
    articulo: IArticulo;
    usuario: IUsuario;
}