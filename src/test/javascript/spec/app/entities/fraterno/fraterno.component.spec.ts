/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EfuTestModule } from '../../../test.module';
import { FraternoComponent } from 'app/entities/fraterno/fraterno.component';
import { FraternoService } from 'app/entities/fraterno/fraterno.service';
import { Fraterno } from 'app/shared/model/fraterno.model';

describe('Component Tests', () => {
  describe('Fraterno Management Component', () => {
    let comp: FraternoComponent;
    let fixture: ComponentFixture<FraternoComponent>;
    let service: FraternoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [FraternoComponent],
        providers: []
      })
        .overrideTemplate(FraternoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FraternoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FraternoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Fraterno(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fraternos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
