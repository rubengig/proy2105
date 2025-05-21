import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { ComentarioService } from '../../../service/comentario.service';
import { IComentario } from '../../../model/comentario.interface';
import { IPage } from '../../../model/model.interface';
import { BotoneraService } from '../../../service/botonera.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-comentario-plist-unrouted',
  templateUrl: './comentario.plist.unrouted.component.html',
  styleUrls: ['./comentario.plist.unrouted.component.css'],
  standalone: true,
  imports: [
    FormsModule]
})
export class ComentarioPlistUnroutedComponent implements OnInit {

  @Input() id_articulo!: number;

  arrBotonera: string[] = [];
  oPage: IPage<IComentario> | null = null;
  nPage: number = 0; // 0-based server count
  nRpp: number = 10;
  nuevoComentario: string = '';

  constructor(
    private oComentarioService: ComentarioService,
    private oBotoneraService: BotoneraService,
  ) { }

  ngOnInit() {
    
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['id_articulo'] && changes['id_articulo'].currentValue) {
      this.getPage();
    }
  }

  getPage() {
    this.oComentarioService
      .getPageXArticulo(0, 10, '', '', '', this.id_articulo)
      .subscribe({
        next: (oPageFromServer: IPage<IComentario>) => {
          this.oPage = oPageFromServer;
          this.arrBotonera = this.oBotoneraService.getBotonera(
            this.nPage,
            oPageFromServer.totalPages
          );
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  addComentario() {
    if (this.nuevoComentario.trim()) {
      const comentario: IComentario = {
        id: 0,
        texto: this.nuevoComentario,
        articulo: { id: this.id_articulo, nombre: '', descripcion: '', precio: 0, favoritos: 0, comentarios: 0 },  // No necesitamos los datos completos del artículo, solo el ID
        usuario: { id: 1, nombre: '', apellido1: '', apellido2: '', email: '', password: '', tipousuario: { id: 1, usuarios: 1, descripcion: 'admin' } },  // Usuario con ID 1
      };

      this.oComentarioService.create(comentario).subscribe({
        next: (res) => {
          this.getPage();  
          this.nuevoComentario = ''; 
        },
        error: (err) => {
          console.error('Error al añadir comentario:', err);
        }
      });
    }
  }
}
