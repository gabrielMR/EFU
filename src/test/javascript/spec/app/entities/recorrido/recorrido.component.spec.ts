/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EfuTestModule } from '../../../test.module';
import { RecorridoComponent } from 'app/entities/recorrido/recorrido.component';
import { RecorridoService } from 'app/entities/recorrido/recorrido.service';
import { Recorrido } from 'app/shared/model/recorrido.model';

describe('Component Tests', () => {
  describe('Recorrido Management Component', () => {
    let comp: RecorridoComponent;
    let fixture: ComponentFixture<RecorridoComponent>;
    let service: RecorridoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [RecorridoComponent],
        providers: []
      })
        .overrideTemplate(RecorridoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecorridoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecorridoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Recorrido(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.recorridos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
