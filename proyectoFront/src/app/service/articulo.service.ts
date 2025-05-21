import { Injectable } from '@angular/core';
import { IArticulo } from '../model/articulo.interface';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { IPage } from '../model/model.interface';
import { httpOptions, serverURL } from '../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class ArticuloService {
  serverURL: string = serverURL + '/articulo';

constructor(private oHttp: HttpClient) { }

 getPage(
    page: number,
    size: number,
    field: string,
    dir: string,
    filtro: string
  ): Observable<IPage<IArticulo>> {
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
    return this.oHttp.get<IPage<IArticulo>>(URL, httpOptions);
  }

  get(id: number): Observable<IArticulo> {
      let URL: string = '';
      URL += this.serverURL;
      URL += '/' + id;
      return this.oHttp.get<IArticulo>(URL);
    }

  update(oArticulo: IArticulo): Observable<IArticulo> {
      let URL: string = '';
      URL += this.serverURL;
      URL += '/update ';
      return this.oHttp.post<IArticulo>(URL, oArticulo);
    }

  getOne(id: number): Observable<IArticulo> {
      let URL: string = '';
      URL += this.serverURL;
      URL += '/' + id;
      return this.oHttp.get<IArticulo>(URL);
    }
  
  delete(id: number) {
      return this.oHttp.delete(this.serverURL + '/delete/' + id);
    }  



    

}
