/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { FraternoDetailComponent } from 'app/entities/fraterno/fraterno-detail.component';
import { Fraterno } from 'app/shared/model/fraterno.model';

describe('Component Tests', () => {
  describe('Fraterno Management Detail Component', () => {
    let comp: FraternoDetailComponent;
    let fixture: ComponentFixture<FraternoDetailComponent>;
    const route = ({ data: of({ fraterno: new Fraterno(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [FraternoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FraternoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FraternoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fraterno).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
