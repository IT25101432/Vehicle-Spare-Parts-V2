-- Diagnostic: Check all users and their enabled status
SELECT id, username, email, enabled
FROM users
ORDER BY id;

-- Fix: Re-enable all non-admin users that are currently disabled
-- (Run the SELECT above first to verify which users are affected)
UPDATE users
SET enabled = 1
WHERE enabled = 0;

-- Verify the fix
SELECT id, username, email, enabled
FROM users
ORDER BY id;
