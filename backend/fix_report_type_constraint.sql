-- Diagnostic: Inventory reports fail with HTTP 500
--   POST /api/reports  {"reportType":"INVENTORY_SUMMARY", ...}
--   -> DataIntegrityViolationException: The INSERT statement conflicted with the
--      CHECK constraint "CK__report_co__repor__4222D4EF", column 'report_type'.
--
-- Cause: the table's CHECK constraint was created from an older schema and allowed
--        INVENTORY_LOW_STOCK, but the Java enum (ReportType) and the frontend both
--        use INVENTORY_SUMMARY. SALES_SUMMARY and PROCUREMENT_SUMMARY were unaffected,
--        which is why only inventory reports failed.
--
-- Run: sqlcmd -S localhost,1433 -U sa -P <pw> -d spareparts_db -i fix_report_type_constraint.sql

-- 1. Inspect the current constraint
SELECT name, OBJECT_DEFINITION(object_id) AS definition
FROM sys.check_constraints
WHERE parent_object_id = OBJECT_ID('report_configs');

-- Original definition (kept here so this change is reversible):
--   ([report_type]='PROCUREMENT_SUMMARY' OR [report_type]='INVENTORY_LOW_STOCK' OR [report_type]='SALES_SUMMARY')

-- 2. Replace it so it matches the ReportType enum
ALTER TABLE report_configs DROP CONSTRAINT CK__report_co__repor__4222D4EF;

ALTER TABLE report_configs ADD CONSTRAINT CK_report_configs_report_type
    CHECK (report_type IN ('SALES_SUMMARY', 'INVENTORY_SUMMARY', 'PROCUREMENT_SUMMARY'));

-- 3. Verify
SELECT name, OBJECT_DEFINITION(object_id) AS definition
FROM sys.check_constraints
WHERE parent_object_id = OBJECT_ID('report_configs');

-- Note: spring.jpa.hibernate.ddl-auto=update does NOT manage CHECK constraints, so this
-- will need re-applying if the database is ever rebuilt from the old schema script.
