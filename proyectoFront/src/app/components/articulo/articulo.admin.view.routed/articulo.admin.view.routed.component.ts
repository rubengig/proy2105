import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { IArticulo } from './../../../model/articulo.interface';
import { ArticuloService } from '../../../service/articulo.service';

@Component({
  selector: 'app-articulo.admin.view.routed',
  templateUrl: './articulo.admin.view.routed.component.html',
  styleUrls: ['./articulo.admin.view.routed.component.css']
})
export class ArticuloAdminViewRoutedComponent implements OnInit {

    id: number = 0;
    route: string = '';
    oArticulo: IArticulo = {} as IArticulo;
    numeroPedidos: number = 0;
    //
    constructor(private oActivatedRoute: ActivatedRoute, private oArticuloService: ArticuloService) { }

    ngOnInit() {
      this.id = this.oActivatedRoute.snapshot.params['id'];
  
      //llamada
      this.getOne();
    }

    getOne() {
        this.oArticuloService.getOne(this.id).subscribe({
          next: (data: IArticulo) => {
            this.oArticulo = data;
          },
        });
      }

}
