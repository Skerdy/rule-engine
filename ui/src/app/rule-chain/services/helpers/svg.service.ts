import {Injectable} from '@angular/core';
import * as SvgPanZoom from 'svg-pan-zoom';

const svgPanZoom = SvgPanZoom;

@Injectable({
  providedIn: 'root'
})
export class SVGService {
  private panZoomInstance: any;

  constructor() {
  }

  initializePanZoom() {
    this.panZoomInstance = svgPanZoom('#dropzone', {
      zoomEnabled: true,
      controlIconsEnabled: true,
      fit: false,
      center: false,
      minZoom: 0.1
    });
  }

  getSVGPoint(event, element) {
    // get the mouse coordinates and set them to the SVG point
    const CTM = element.getScreenCTM();
    return {
      x: (event.clientX - CTM.e) / CTM.a,
      y: (event.clientY - CTM.f) / CTM.d
    };
  }
}
