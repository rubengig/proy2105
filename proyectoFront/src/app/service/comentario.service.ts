import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { IPage } from '../model/model.interface';
import { httpOptions, serverURL } from '../environment/environment';
import { IComentario } from './../model/comentario.interface';

@Injectable({
  providedIn: 'root'
})
export class ComentarioService {
  serverURL: string = serverURL + '/comentario';

  constructor(private oHttp: HttpClient) { }

  getPage(
    page: number,
    size: number,
    field: string,
    dir: string,
    filtro: string
  ): Observable<IPage<IComentario>> {
    let URL: string = '';
    URL += this.serverURL;
    if (!page) {
      page = 0;
    }
    URL += '?page=' + page;
    if (!size) {
      size = 10;
    }
    URL += '&size=' + size;
    if (field) {
      URL += '&sort=' + field;
      if (dir === 'asc') {
        URL += ',asc';
      } else {
        URL += ',desc';
      }
    }
    if (filtro) {
      URL += '&filter=' + filtro;
    }
    return this.oHttp.get<IPage<IComentario>>(URL, httpOptions);
  }

  get(id: number): Observable<IComentario> {
    let URL: string = '';
    URL += this.serverURL;
    URL += '/articulo/' + id;
    return this.oHttp.get<IComentario>(URL);
  }

  getOne(id: number): Observable<IComentario> {
    let URL: string = '';
    URL += this.serverURL;
    URL += '/articulo/' + id;
    return this.oHttp.get<IComentario>(URL);
  }

  delete(id: number) {
    return this.oHttp.delete(this.serverURL + '/delete/' + id);
  }


  getPageXArticulo(
    page: number,
    size: number,
    field: string,
    dir: string,
    filtro: string,
    id_articulo: number
  ): Observable<IPage<IComentario>> {
    let URL: string = '';
    URL += this.serverURL + "/articulo/" + id_articulo;
    if (!page) {
      page = 0;
    }
    URL += '?page=' + page;
    if (!size) {
      size = 10;
    }
    URL += '&size=' + size;
    if (field) {
      URL += '&sort=' + field;
      if (dir === 'asc') {
        URL += ',asc';
      } else {
        URL += ',desc';
      }
    }
    if (filtro) {
      URL += '&filter=' + filtro;
    }
    return this.oHttp.get<IPage<IComentario>>(URL, httpOptions);
  }

  create(comentario: IComentario): Observable<IComentario> {
  let URL: string = this.serverURL;
  return this.oHttp.post<IComentario>(URL, comentario, httpOptions);
}


}
