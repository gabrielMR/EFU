/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EfuTestModule } from '../../../test.module';
import { FraternidadComponent } from 'app/entities/fraternidad/fraternidad.component';
import { FraternidadService } from 'app/entities/fraternidad/fraternidad.service';
import { Fraternidad } from 'app/shared/model/fraternidad.model';

describe('Component Tests', () => {
  describe('Fraternidad Management Component', () => {
    let comp: FraternidadComponent;
    let fixture: ComponentFixture<FraternidadComponent>;
    let service: FraternidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [FraternidadComponent],
        providers: []
      })
        .overrideTemplate(FraternidadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FraternidadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FraternidadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Fraternidad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fraternidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
