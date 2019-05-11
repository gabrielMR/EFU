/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EfuTestModule } from '../../../test.module';
import { ComunicadoComponent } from 'app/entities/comunicado/comunicado.component';
import { ComunicadoService } from 'app/entities/comunicado/comunicado.service';
import { Comunicado } from 'app/shared/model/comunicado.model';

describe('Component Tests', () => {
  describe('Comunicado Management Component', () => {
    let comp: ComunicadoComponent;
    let fixture: ComponentFixture<ComunicadoComponent>;
    let service: ComunicadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [ComunicadoComponent],
        providers: []
      })
        .overrideTemplate(ComunicadoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComunicadoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComunicadoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Comunicado(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.comunicados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
