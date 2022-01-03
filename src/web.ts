import { WebPlugin } from '@capacitor/core';

import type { ImageCropPlugin } from './definitions';

export class ImageCropWeb extends WebPlugin implements ImageCropPlugin {

  async show(options: {
    source: string;
    width?: number;
    height?: number;
    lock?: boolean;
    aspectRatio?: {
      x: number;
      y: number
    }
  }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return {
      value: 'not implemented'
    };
  }
}
