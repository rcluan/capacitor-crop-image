export interface ImageCropPlugin {
  show(options: {
    source: string;
    width?: number;
    height?: number;
    lock?: boolean;
    aspectRatio?: {
      x: number;
      y: number
    }
  }): Promise<{
      value: string;
  }>;
}
