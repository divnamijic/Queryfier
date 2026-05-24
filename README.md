# EdgeConverterG6

## 📌 Overview

EdgeConverterG6 (Queryfier) is a data modeling and conversion tool that allows users to upload `.edg` files, visually edit database structures, and export them into SQL-based formats such as PostgreSQL.

The application bridges the gap between conceptual database design and real-world database implementation by enabling intuitive management of entities, attributes, and relationships.

---

## ⚙️ Features

- 📂 **.edg File Import**
  - Upload and parse `.edg` database schema files

- 🧱 **Visual Schema Editor**
  - Edit tables (entities) and attributes
  - Add, remove, and modify columns
  - Define primary and foreign keys

- 🔗 **Relationship Management**
  - Manage relationships between tables
  - Support for one-to-one, one-to-many, and many-to-many relations

- ✅ **Validation System**
  - Primary key validation
  - Relationship integrity checks
  - Schema consistency verification

- 📤 **Export Options**
  - Export to SQL format
  - Export to PostgreSQL-compatible scripts

---

## 🛠 Tech Stack

- JavaFX (User Interface)
- TypeScript (Application logic)
- PostgreSQL (Database target)
- Custom `.edg` parser

---

## 🔄 Workflow

1. Upload `.edg` file
2. System parses schema into entities and relationships
3. User edits tables, attributes, and relationships
4. Validation is performed automatically
5. Export final schema to SQL or PostgreSQL format

---

## 👥 Members

- [ ] Swen Grgicevic
- [ ] Petra Cesar
- [ ] Doroteja Krtalic
- [ ] Michel Brassard
- [ ] Divna Mijic

---

## 📘 Note

This project was developed collaboratively with my colleagues as part of the App Development Practices course.

## 🎯 Purpose

This project helps simplify the transition from database design to implementation by providing a visual and interactive way to transform `.edg` models into executable SQL schemas.

---

## 🚀 Future Improvements

- MySQL export support
- Improved ER diagram visualization
- Cloud-based schema storage
- Collaborative editing features
