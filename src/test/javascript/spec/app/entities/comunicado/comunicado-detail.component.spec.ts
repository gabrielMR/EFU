/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { ComunicadoDetailComponent } from 'app/entities/comunicado/comunicado-detail.component';
import { Comunicado } from 'app/shared/model/comunicado.model';

describe('Component Tests', () => {
  describe('Comunicado Management Detail Component', () => {
    let comp: ComunicadoDetailComponent;
    let fixture: ComponentFixture<ComunicadoDetailComponent>;
    const route = ({ data: of({ comunicado: new Comunicado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [ComunicadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComunicadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComunicadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comunicado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
