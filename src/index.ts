import { registerPlugin } from '@capacitor/core';

import type { ImageCropPlugin } from './definitions';

const ImageCrop = registerPlugin<ImageCropPlugin>('ImageCrop', {
  web: () => import('./web').then(m => new m.ImageCropWeb()),
});

export * from './definitions';
export { ImageCrop };
