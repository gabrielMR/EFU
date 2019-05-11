/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EfuTestModule } from '../../../test.module';
import { DelegadoComponent } from 'app/entities/delegado/delegado.component';
import { DelegadoService } from 'app/entities/delegado/delegado.service';
import { Delegado } from 'app/shared/model/delegado.model';

describe('Component Tests', () => {
  describe('Delegado Management Component', () => {
    let comp: DelegadoComponent;
    let fixture: ComponentFixture<DelegadoComponent>;
    let service: DelegadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [DelegadoComponent],
        providers: []
      })
        .overrideTemplate(DelegadoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DelegadoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelegadoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Delegado(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.delegados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
