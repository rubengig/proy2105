import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { IArticulo } from './../../../model/articulo.interface';
import { ArticuloService } from '../../../service/articulo.service';
import { ComentarioPlistUnroutedComponent } from "../../comentario/comentario.plist.unrouted/comentario.plist.unrouted.component";

@Component({
  selector: 'app-articulo.user.view.routed',
  templateUrl: './articulo.user.view.routed.component.html',
  styleUrls: ['./articulo.user.view.routed.component.css'],
  imports: [ComentarioPlistUnroutedComponent],
  standalone: true,
})
export class ArticuloUserViewRoutedComponent implements OnInit {
  
  id: number = 0;
  route: string = '';
  oArticulo: IArticulo = {} as IArticulo;
  numeroPedidos: number = 0;
  //
  constructor(private oActivatedRoute: ActivatedRoute, private oArticuloService: ArticuloService) {
    

    
   }

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
