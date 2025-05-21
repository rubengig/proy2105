import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';;
import { ArticuloService } from '../../../service/articulo.service';
import { IArticulo } from '../../../model/articulo.interface';

declare let bootstrap: any;

@Component({
  selector: 'app-articulo.admin.delete.routed',
  templateUrl: './articulo.admin.delete.routed.component.html',
  styleUrls: ['./articulo.admin.delete.routed.component.css'],
  standalone: true,
  imports: [RouterModule]
})
export class ArticuloAdminDeleteRoutedComponent implements OnInit {

    oArticulo: IArticulo | null = null;
    strMessage: string = '';
    myModal: any;

  constructor(
    private oArticuloService: ArticuloService,
    private oActivatedRoute: ActivatedRoute,
    private oRouter: Router
  ) { }

   ngOnInit(): void {
     let id = this.oActivatedRoute.snapshot.params['id'];
     this.oArticuloService.get(id).subscribe({
       next: (oArticulo: IArticulo) => {
         this.oArticulo = oArticulo;
       },
       error: (err) => {
         this.showModal('Error al cargar el Articulo');
       },
     });
   }

   showModal(mensaje: string) {
    this.strMessage = mensaje;
    this.myModal = new bootstrap.Modal(document.getElementById('mimodal'), {
      keyboard: false,
    });
    this.myModal.show();
  }

  delete(): void {
    this.oArticuloService.delete(this.oArticulo!.id).subscribe({
      next: (data) => {
        this.showModal(
          'Articulo con id ' + this.oArticulo!.id + ' ha sido borrado'
        );
      },
      error: (error) => {
        this.showModal('Error al borrar el articulo');
      },
    });
  }

  hideModal = () => {
    this.myModal.hide();
    this.oRouter.navigate(['/admin/articulo/plist']);
  }

}
