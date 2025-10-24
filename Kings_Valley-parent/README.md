# King’s Valley Remake

A [libGDX](https://libgdx.com/) project inspired by *King’s Valley* (Konami, 1985).  
Developed as a non-commercial educational remake that recreates the classic gameplay using modern Java and LibGDX architecture.

---

## 🕹️ About the Project

This remake aims to preserve and study the original design and mechanics of *King’s Valley* while implementing them in a clean, modular codebase.  
It serves as both a technical exercise and a homage to one of the great puzzle-platform games of the 8-bit era.

---

## 🧩 Platforms

- `core`: Main module with the shared game logic.  
- `lwjgl3`: Desktop platform using LWJGL3 (previously known as “desktop” in older LibGDX docs).

---

## ⚙️ Gradle

This project uses [Gradle](https://gradle.org/) for dependency management and builds.

Useful Gradle tasks and flags:

- `build`: builds sources and archives of every project.  
- `clean`: removes `build` folders.  
- `lwjgl3:jar`: builds the runnable JAR (`lwjgl3/build/libs`).  
- `lwjgl3:run`: runs the game directly.  
- `test`: runs unit tests (if any).  

You can also use:
- `--continue` → don’t stop on errors  
- `--daemon` → use Gradle daemon for faster builds  
- `--offline` → use cached dependencies  
- `--refresh-dependencies` → revalidate dependencies  

---

## 📜 License

This project is a non-commercial remake of *King’s Valley* (Konami, 1985).  
It is distributed under the **Creative Commons Attribution–NonCommercial 4.0 International (CC BY-NC 4.0)** license.  
See [LICENSE](./LICENSE) for details.
