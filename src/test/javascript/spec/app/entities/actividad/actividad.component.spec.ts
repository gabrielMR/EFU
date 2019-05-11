/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EfuTestModule } from '../../../test.module';
import { ActividadComponent } from 'app/entities/actividad/actividad.component';
import { ActividadService } from 'app/entities/actividad/actividad.service';
import { Actividad } from 'app/shared/model/actividad.model';

describe('Component Tests', () => {
  describe('Actividad Management Component', () => {
    let comp: ActividadComponent;
    let fixture: ComponentFixture<ActividadComponent>;
    let service: ActividadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [ActividadComponent],
        providers: []
      })
        .overrideTemplate(ActividadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActividadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActividadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Actividad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.actividads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
