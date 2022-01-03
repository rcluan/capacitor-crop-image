import { Component, SecurityContext } from '@angular/core';
import { Camera, CameraResultType, CameraSource, ImageOptions } from '@capacitor/camera';
import { Filesystem, Directory } from '@capacitor/filesystem';
import { ImageCrop } from '../../../../dist/esm';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  picture: string;

  constructor(
    private sanitizer: DomSanitizer
  ) {}

  async camera() {
    this.open(CameraSource.Camera);
  }

  async gallery() {
    this.open(CameraSource.Photos);
  }

  private async open(source: CameraSource) {
    const options = {
      resultType: CameraResultType.Uri,
      source
    } as ImageOptions;

    const photo = await Camera.getPhoto(options);

    const response = await ImageCrop.show({
      source: photo.path,
      width: 640,
      height: 640,
      aspectRatio: {
        x: 1,
        y: 1
      }
    });

    const imageData = response.value;
    
    const pathContent = imageData.split("/");
    const filename = pathContent[pathContent.length - 1];
    
    const pictureFile = await Filesystem.readFile({
      path: filename,
      directory: Directory.Cache
    });

    this.picture = this.sanitizer.sanitize(SecurityContext.RESOURCE_URL, this.sanitizer.bypassSecurityTrustResourceUrl(imageData));

    console.log(this.picture);
  }

}
