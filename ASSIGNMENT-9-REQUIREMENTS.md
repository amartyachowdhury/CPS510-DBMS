# Assignment 9 Requirements (Based on Official Rubric)

**Total Marks: 3 marks + 3 bonus marks (for GUI/Web application)**

## ✅ Core Assignment Requirements (3 marks)

### 1. Demo of Menu with UI (1.0 mark)

**Requirements:**
- [x] **Drop Tables** option implemented ✅ **DONE**
- [x] **Create Tables** option implemented ✅ **DONE**
- [x] **Populate Tables** option implemented ✅ **DONE**
- [x] **Query Tables** option implemented ✅ **DONE**
- [x] **Exit** option implemented ✅ **DONE** (Back to Home serves as exit)
- [x] UI connected to database (school or localhost) ✅ **DONE**

**Current Status:**
- ✅ Web-based UI implemented
- ✅ Connected to Ryerson Oracle database
- ✅ **Drop Tables functionality** - Admin page with confirmation dialog
- ✅ **Create Tables functionality** - Recreates all tables, sequences, triggers, views
- ✅ **Populate Tables functionality** - Inserts sample data into all tables
- ✅ **Query Tables functionality** - Shows summary with row counts for all tables/views
- ✅ **Admin menu page** at `/admin` with all schema management options

**To achieve 1.0 mark (Excellent):**
- All options must work correctly
- Can be command-line menu OR web interface
- UI must be connected to database

---

### 2. Normalization, Database Schema & Content (0.5 marks)

**Requirements:**
- [x] Database in 3NF & BCNF ✅ **DONE** (from previous assignments)
- [x] Database contains sample dummy data ✅ **DONE** (from SQL files)
- [ ] Code properly formatted & commented ⚠️ **NEEDS REVIEW**

**Current Status:**
- ✅ Database schema is normalized (3NF & BCNF)
- ✅ Sample data exists in database
- ⚠️ Need to review code comments, especially for:
  - Advanced reports/queries
  - Complex variables and functions
  - DAO methods
  - Controller methods

**To achieve 0.5 marks (Excellent):**
- Code must be properly formatted
- Comments required for advanced reports, variables, and functions

---

### 3. Functionality: SQL Queries & Code Structure (0.5 marks)

**Requirements:**
- [x] **Read records** ✅ **DONE** (all entities have list/view)
- [x] **Update records** ✅ **DONE** (all entities have edit functionality)
- [x] **Delete records** ✅ **DONE** (all entities have delete functionality)
- [x] **Search for specific records** ✅ **DONE** (search implemented for all entities)
- [ ] Code properly formatted & commented ⚠️ **NEEDS REVIEW**

**Current Status:**
- ✅ Read, Update, Delete implemented for all entities
- ✅ **Search functionality implemented** - All list pages have search forms
  - Customers: search by name, email, or phone
  - Products: search by name, brand, color, or category
  - Orders: search by order ID, customer name, employee name, or status
  - Payments: search by payment ID, order ID, method, status, or amount
- ⚠️ Code comments need review

**To achieve 0.5 marks (Excellent):**
- Must have search functionality for finding specific records
- Code must be properly formatted and commented

---

### 4. Individual Evaluation (1.0 mark)

**Requirements:**
- [ ] Student can elaborate on:
  - Functionality of queries
  - Implemented UI/GUI
  - Database schema and design decisions

**This is a presentation/demo requirement** - you need to be prepared to explain your work.

**To achieve 1.0 mark (Excellent):**
- Be able to explain all queries and their purpose
- Be able to demonstrate and explain the UI
- Understand your database schema

---

## 🎁 Bonus Requirements (3 marks - Deadline week 30)

### Bonus 1: Front End Access by GUI/Web (1.5 marks)

**Requirements:**
- [x] Menu implemented by GUI or Web-based interface ✅ **DONE**
- [x] Menu options are functional through GUI/Web-UI ✅ **DONE**

**Current Status:**
- ✅ Web application implemented
- ✅ All CRUD operations accessible through web UI
- ✅ Navigation menu implemented

**To achieve 1.5 marks (Excellent):**
- All menu options must work correctly
- Minor issues are acceptable for 1.25 marks

---

### Bonus 2: Separate Access to Forms and Tables (1.5 marks)

**Requirements:**
- [x] Access and update records in each table/form ✅ **DONE**
- [x] Complete database application or web application ✅ **DONE**

**Current Status:**
- ✅ Separate pages/forms for Customers
- ✅ Separate pages/forms for Products  
- ✅ Separate pages/forms for Orders
- ✅ Separate pages/forms for Payments
- ✅ Each entity has: List, View, Create, Edit, Delete

**To achieve 1.5 marks (Excellent):**
- All tables/forms must be accessible and functional
- Minor issues are acceptable for 1.25 marks

---

## 📋 Action Items to Complete

### High Priority (Required for full marks):

1. **Add Search Functionality** (Required - 0.5 marks) ✅ **COMPLETED**
   - ✅ Add search to Customers page
   - ✅ Add search to Products page
   - ✅ Add search to Orders page
   - ✅ Add search to Payments page

2. **Add Menu Options for Schema Management** (Required - 1.0 mark) ✅ **COMPLETED**
   - ✅ Created admin/management page at `/admin` with options:
     - ✅ Drop Tables (with confirmation dialog)
     - ✅ Create Tables (recreates complete schema)
     - ✅ Populate Tables (inserts sample data)
     - ✅ Query Tables (shows table summaries with row counts)
   - ✅ All options functional through web interface
   - ✅ Accessible from main navigation menu

3. **Add Code Comments** (Required - 0.5 + 0.5 marks)
   - Review all DAO classes - add comments for complex queries
   - Review all Controller classes - add comments for methods
   - Review model classes - add JavaDoc comments
   - Comment complex business logic (e.g., order status updates)

### Medium Priority (Presentation preparation):

4. **Prepare for Individual Evaluation** (Required - 1.0 mark)
   - Practice explaining your queries
   - Practice demonstrating the UI
   - Understand database schema and normalization
   - Prepare answers for common questions

---

## 🎯 Expected Score

**Core Assignment (3 marks):**
- Menu with UI: 1.0 mark ✅ (all menu options implemented)
- Normalization & Schema: 0.5 marks (need code comments)
- SQL Queries & Code: 0.5 marks (✅ search done, need comments)
- Individual Evaluation: 1.0 mark (presentation required)

**Bonus (3 marks):**
- Front End Access: 1.5 marks ✅
- Separate Forms/Tables: 1.5 marks ✅

**Total Potential: 6 marks**

---

## 📝 Submission Checklist

- [ ] Source code files
- [ ] README.md with setup instructions
- [ ] Screenshots of application running
- [ ] Database connection instructions
- [x] All menu options functional ✅
- [x] Search functionality working ✅
- [ ] Code properly commented
- [ ] Ready for demo/presentation

---

## 🔧 Quick Wins

1. ✅ **Add Search Bar** to each list page (Customers, Products, Orders, Payments) - **COMPLETED**
2. ✅ **Add Admin Menu** page with Drop/Create/Populate/Query options - **COMPLETED**
3. **Add JavaDoc Comments** to all classes and methods
4. **Test all functionality** before submission

