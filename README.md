\# King's Valley Remake



A modern Java and libGDX remake of \*King's Valley\* (Konami, 1985), developed as a non-commercial educational project focused on software architecture and Object-Oriented Programming (OOP) concepts.



Current version: **1.2.2**

DOI: https://doi.org/10.5281/zenodo.20789719


\## 🕹️ About the Project



This project recreates the gameplay and level design of the original \*King's Valley\* while implementing the game using a clean, modular, and maintainable software architecture.



The remake was developed as part of a research effort on the use of video games for teaching Object-Oriented Programming. Special emphasis was placed on the separation between game logic and graphical rendering, allowing the project to serve as a case study for software design and architecture.



\---



\## 🎓 Educational Purpose



The project is intended to support the teaching and learning of software engineering and Object-Oriented Programming concepts, including:



\* Encapsulation

\* Inheritance

\* Polymorphism

\* Separation of concerns

\* Interface-based design

\* Design patterns

\* Architectural decoupling



Unlike many game development approaches that tightly couple gameplay logic with engine-specific components, this project explicitly separates the game model from the graphical representation layer.



The source code is publicly available to facilitate educational use, reproducibility, and further research.



\---



\## 🧩 Project Structure



\* `core` — Shared game logic, domain model, gameplay rules, and architecture.

\* `lwjgl3` — Desktop launcher and platform-specific implementation using LWJGL3.



The game logic is intentionally designed to remain independent from the rendering implementation whenever possible.



\---



\## 🗺️ Levels



Levels are defined using TMX maps created with Tiled.



The level layouts faithfully reproduce the original \*King's Valley\* stages while being implemented using modern development tools and formats.



\---



\## ⚙️ Building the Project



This project uses Gradle for dependency management and builds.



Common Gradle tasks:



\* `build` — Build all modules.

\* `clean` — Remove build artifacts.

\* `lwjgl3:run` — Run the game.

\* `lwjgl3:jar` — Create the executable JAR.

\* `test` — Execute automated tests.



Useful Gradle flags:



\* `--continue`

\* `--daemon`

\* `--offline`

\* `--refresh-dependencies`



\---



\## 🚧 Project Status



Current version: \*\*1.2.2\*\*



The project is actively maintained and continues to evolve as both a playable remake and an educational case study.



\---



## 📖 Citation

If you use this software for educational, research, or academic purposes, please cite the archived software record:

DOI: https://doi.org/10.5281/zenodo.20789719

Suggested citation:

Lazzurri, G. (2026). King's Valley Remake (v1.2.2). Zenodo. https://doi.org/10.5281/zenodo.20789719

\## 📚 Related Research



This software accompanies ongoing research on the use of video games for teaching Object-Oriented Programming through architectures that decouple game logic from graphical rendering.



\---



\## 📜 License



This project is distributed under the terms described in the LICENSE file.



All source code, graphics, audio resources, and assets included in this repository are original creations or derived from resources distributed under licenses that permit their use in this project.



\*King's Valley\* is a trademark and copyrighted work originally created by Konami. This project is an unofficial, non-commercial educational remake and is not affiliated with, endorsed by, or sponsored by Konami.



