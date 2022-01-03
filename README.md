# @pinnpet/crop-image

Capacitor 3 Image Crop

OBS: works only for Android. For iOS I recommend using capacitor's camera allowEdit option.

The demo example uses Ionic.

## Install

```bash
npm install @pinnpet/crop-image
npx cap sync
```

## API

<docgen-index>

* [`show(...)`](#show)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### show(...)

```typescript
show(options: { source: string; width?: number; height?: number; lock?: boolean; aspectRatio?: { x: number; y: number; }; }) => Promise<{ value: string; }>
```

| Param         | Type                                                                                                                       |
| ------------- | -------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ source: string; width?: number; height?: number; lock?: boolean; aspectRatio?: { x: number; y: number; }; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------

</docgen-api>
