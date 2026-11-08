# Tm-UI-Kit

The Compose Multiplatform UI kit shared by TicketMan and Tuners Toolkit (Android, desktop, web and iOS). Every
component is prefixed `Tm`, in package `org.mr0btain.uikit`:

| Package | What's there |
|---|---|
| `theme` | `TmTheme` (the entry point), `TmColors`, `TmPalettes` (Bench, Copper, Mono, each dark and light), `TmFonts` (Barlow, Atkinson Hyperlegible, System, with JetBrains Mono for numbers), typography, shapes, spacing |
| `foundation` | `TmBackdrop`, glass (`TmGlassSurface`, `Modifier.glass()`, `glassSource()`) and solid `TmSurface`, hover/press/focus, control sizes, scrollbar |
| `buttons` | `TmButton`, `TmIconButton`, `TmButtonRow` |
| `inputs` | `TmTextField`, `TmPasswordField`, `TmFieldFrame`, `TmFieldPair` |
| `selection` | `TmDropdown`, `TmMenu`, `TmSegmented`, `TmFilterChip`, `TmSwitch`, `TmCheckbox`, `TmRadio` |
| `search` | `TmSearchField`, `highlightMatch` |
| `containers` | `TmPanel`, `TmDialog`, `TmConfirmDialog`, `TmConfirmDeleteDialog`, `TmTextEditDialog`, `TmFullScreenDialog`, `TmPageHeader`, `TmListRow`, `TmTabs`, `TmLazyColumn`, `TmScrollColumn` |
| `feedback` | `TmBadge`, `TmDot`, `TmNotice`, `TmSnackbarContent`, `TmEmptyState`, `TmLoading` |
| `data` | `TmTable`, `TmStatTile`, `TmCountBar`, `TmKeyValueRow`, `TmLogTerminal` (shows Tm-AppLogger's lines) |
| `navigation` | `TmAppShell` (a nav rail on wide windows, a bottom bar on phones, the backdrop and snackbar host), `TmNavRail`, `TmBottomNav`, `TmTopBar`, `TmStepper` |
| `gallery` | `KitGallery`: every component in every state, with theme and font pickers |

Fonts and icons ship inside the library, under `org.mr0btain.uikit.resources.Res`.

## Rules

Screens get colors, type, shapes and spacing from `TmTheme.colors/typography/shapes/spacing` and never hard code
them. Glass is for floating layers only (rails, bars, dialogs, menus, snackbars); cards and lists use solid
`TmSurface`. Numbers use the `numeric*` styles.

`tm-ui-kit-rules` checks an app's screens for this. Run it from the app's tests:

```kotlin
val problems = KitRules.checkFiles(root, screenFiles)
assertTrue(problems.isEmpty(), problems.joinToString("\n"))
```

It fails on `Color(0x`, named colors (not `Transparent`/`Unspecified`), `fontSize =`, `TextStyle(`,
`RoundedCornerShape(`, `MaterialTheme.`, Material 2 imports, and Material 3 imports other than `Text`, `Icon`,
`SnackbarHostState` and `windowsizeclass`. Need something new? Add it to the kit (or as the app's own `Tm*`
component built from kit pieces), give it a gallery entry, then use it.

## Using it in an app

```kotlin
// libs.versions.toml
tm-ui-kit = { module = "org.mr0btain:tm-ui-kit", version = "0.1.0" }
tm-ui-kit-rules = { module = "org.mr0btain:tm-ui-kit-rules", version = "0.1.0" }

// composeApp/build.gradle.kts
commonMain.dependencies { implementation(libs.tm.ui.kit) }
getByName("desktopTest").dependencies { implementation(libs.tm.ui.kit.rules) }
```

Wrap the app in `TmTheme(palette, font, darkTheme, reduceTransparency) { ... }`. An app adds its own palettes by
passing `TmPalettes.all + itsOwn` to the gallery, and its own components to the gallery with
`KitGallery(extraSections = listOf({ MySection() }))`, building each with `GallerySection` and `Specimen`.

Apps build this from source rather than a Maven release. Their `settings.gradle.kts` includes it with
`includeBuild`, from the first of:

1. `<sharedLibsDir>/Tm-UI-Kit`, with `sharedLibsDir` set in `~/.gradle/gradle.properties` (where you keep checkouts)
2. `../Tm-UI-Kit`, checked out next to the app
3. `.deps/Tm-UI-Kit`, cloned from the public mirror (`https://github.com/Mr0btain/Tm-UI-Kit.git`) at the tag the app
   pins, on its first build

With 1 or 2, a change here shows up in the app straight away. A release is a `v*` tag matching
`tmuikit.version`; an app moves to it by changing the tag in its `settings.gradle.kts` (and the version in its
`libs.versions.toml`). Building from source also means iOS works anywhere there's a Mac, with nothing to publish. This build gets Tm-AppLogger the same way.

## Building

```bash
./gradlew :gallery:run                         # the gallery in a desktop window
./gradlew :tm-ui-kit:desktopTest               # tests; the gallery drawn to tm-ui-kit/build/kit-gallery/*.png
./gradlew :tm-ui-kit-rules:test
./gradlew :tm-ui-kit:compileKotlinWasmJs :tm-ui-kit:compileAndroidMain
```


Fonts are under the SIL Open Font License, see `licenses/`.
