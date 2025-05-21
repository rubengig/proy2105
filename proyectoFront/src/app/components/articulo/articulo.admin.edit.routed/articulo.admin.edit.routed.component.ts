import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ArticuloService } from '../../../service/articulo.service';
import { IArticulo } from '../../../model/articulo.interface';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

declare let bootstrap: any;

@Component({
  selector: 'app-articulo.admin.edit.routed',
  templateUrl: './articulo.admin.edit.routed.component.html',
  styleUrls: ['./articulo.admin.edit.routed.component.css'],
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    RouterModule,
  ],
})
export class ArticuloAdminEditRoutedComponent implements OnInit {

    id: number = 0;
    oArticuloForm: FormGroup | undefined = undefined;
    oArticulo: IArticulo | null = null;
    message: string = '';
  
    myModal: any;

   constructor(
      private oActivatedRoute: ActivatedRoute,
      private oArticuloService: ArticuloService,
      private oRouter: Router
    ) {
      this.oActivatedRoute.params.subscribe((params) => {
        this.id = params['id'];
      });
    }

    ngOnInit() {
      this.createForm();
      this.get();
      this.oArticuloForm?.markAllAsTouched();
    }

    createForm() {
      this.oArticuloForm = new FormGroup({
        id: new FormControl('', [Validators.required]),
        nombre: new FormControl('', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(255),
        ]),
        descripcion: new FormControl('', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(255),
        ]),
        precio: new FormControl(''),
      });
    }

      onReset() {
        this.oArticuloService.get(this.id).subscribe({
          next: (oArticulo: IArticulo) => {
            this.oArticulo = oArticulo;
            this.updateForm();
          },
          error: (error) => {
            console.error(error);
          },
        });
        return false;
      }

      updateForm() {
        this.oArticuloForm?.controls['id'].setValue(this.oArticulo?.id);
        this.oArticuloForm?.controls['nombre'].setValue(this.oArticulo?.nombre);
        this.oArticuloForm?.controls['descripcion'].setValue(this.oArticulo?.descripcion);
        this.oArticuloForm?.controls['precio'].setValue(this.oArticulo?.precio);
      }

    get() {
      this.oArticuloService.get(this.id).subscribe({
        next: (oArticulo: IArticulo) => {
          this.oArticulo = oArticulo;
          this.updateForm();
        },
        error: (error) => {
          console.error(error);
        },
      });
    }

    showModal(mensaje: string) {
      this.message = mensaje;
      this.myModal = new bootstrap.Modal(document.getElementById('mimodal'), {
        keyboard: false,
      });
      this.myModal.show();
    }
  
    hideModal = () => {
      this.myModal.hide();
      this.oRouter.navigate(['/admin/articulo/view/' + this.oArticulo?.id]);
    };

    onSubmit() {
        if (!this.oArticuloForm?.valid) {
          this.showModal('Formulario no válido');
          return;
        } else {
          this.oArticuloService.update(this.oArticuloForm?.value).subscribe({
            next: (oArticulo: IArticulo) => {
              this.oArticulo = oArticulo;
              this.updateForm();
              this.showModal('Usuario ' + this.oArticulo.id + ' actualizado');
            },
            error: (error) => {
              this.showModal('Error al actualizar el articulo');
              console.error(error);
            },
          });
        }
      }

}
