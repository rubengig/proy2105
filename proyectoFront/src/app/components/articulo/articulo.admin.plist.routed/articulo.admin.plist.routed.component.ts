import { Component, OnInit } from '@angular/core';
import { ArticuloService } from '../../../service/articulo.service';
import { IArticulo } from '../../../model/articulo.interface';
import { CommonModule } from '@angular/common';
import { IPage } from '../../../model/model.interface';
import { FormsModule } from '@angular/forms';
import { BotoneraService } from '../../../service/botonera.service';
import { debounceTime, Subject } from 'rxjs';
import { Router, RouterModule } from '@angular/router';
import { TrimPipe } from '../../../pipe/trim.pipe';
import { SharedHeaderUnroutedComponent } from "../../shared/shared.header.unrouted/shared.header.unrouted.component";

@Component({
  selector: 'app-articulo.admin.plist.routed',
  templateUrl: './articulo.admin.plist.routed.component.html',
  styleUrls: ['./articulo.admin.plist.routed.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, TrimPipe, RouterModule, SharedHeaderUnroutedComponent],
})
export class ArticuloAdminPlistRoutedComponent implements OnInit {

    oPage: IPage<IArticulo> | null = null;
    //
    nPage: number = 0; // 0-based server count
    nRpp: number = 10;
    //
    strField: string = '';
    strDir: string = '';
    //
    strFiltro: string = '';
    //
    arrBotonera: string[] = [];
    //
    private debounceSubject = new Subject<string>();
    constructor(
      private oArticuloService: ArticuloService,
      private oBotoneraService: BotoneraService,
      private oRouter: Router
    ) {
      this.debounceSubject.pipe(debounceTime(10)).subscribe((value) => {
        this.getPage();
      });
    }

  ngOnInit() {
    this.getPage();
  }

  getPage() {
      this.oArticuloService
        .getPage(this.nPage, this.nRpp, this.strField, this.strDir, this.strFiltro)
        .subscribe({
          next: (oPageFromServer: IPage<IArticulo>) => {
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

    edit(oArticulo: IArticulo) {
      //navegar a la página de edición
      this.oRouter.navigate(['admin/articulo/edit', oArticulo.id]);
    }
  
    view(oArticulo: IArticulo) {
      //navegar a la página de edición
      this.oRouter.navigate(['admin/articulo/view', oArticulo.id]);
    }
  
    remove(oArticulo: IArticulo) {
      this.oRouter.navigate(['admin/articulo/delete/', oArticulo.id]);
    }

    goToPage(p: number) {
      if (p) {
        this.nPage = p - 1;
        this.getPage();
      }
      return false;
    }
  
    goToNext() {
      this.nPage++;
      this.getPage();
      return false;
    }
  
    goToPrev() {
      this.nPage--;
      this.getPage();
      return false;
    }
  
    sort(field: string) {
      this.strField = field;
      this.strDir = this.strDir === 'asc' ? 'desc' : 'asc';
      this.getPage();
    }
  
    goToRpp(nrpp: number) {
      this.nPage = 0;
      this.nRpp = nrpp;
      this.getPage();
      return false;
    }
  
    filter(event: KeyboardEvent) {
      this.debounceSubject.next(this.strFiltro);
    }

}
