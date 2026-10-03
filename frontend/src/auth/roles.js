export const ROLES = {
  SYSTEM_ADMINISTRATOR: 'ROLE_SYSTEM_ADMINISTRATOR',
  SHOP_OWNER: 'ROLE_SHOP_OWNER',
  STORE_MANAGER: 'ROLE_STORE_MANAGER',
  PROCUREMENT_OFFICER: 'ROLE_PROCUREMENT_OFFICER',
  STORE_KEEPER: 'ROLE_STORE_KEEPER',
  SALES_STAFF: 'ROLE_SALES_STAFF',
  COMPATIBILITY_MANAGER: 'ROLE_COMPATIBILITY_MANAGER',
}

export const FULL_ACCESS = [ROLES.SYSTEM_ADMINISTRATOR, ROLES.SHOP_OWNER, ROLES.STORE_MANAGER]

// Who can see stock alerts (same roles as GET /api/parts)
export const ALERT_ROLES = [...FULL_ACCESS, ROLES.PROCUREMENT_OFFICER, ROLES.STORE_KEEPER, ROLES.SALES_STAFF]

export const NAV_ITEMS = [
  { path: '/', label: 'Dashboard', icon: 'grid', roles: Object.values(ROLES) },
  { path: '/users', label: 'Staff Members', icon: 'users', roles: FULL_ACCESS },
  { path: '/inventory', label: 'Inventory', icon: 'box', roles: [...FULL_ACCESS, ROLES.STORE_KEEPER, ROLES.SALES_STAFF] },
  { path: '/categories', label: 'Categories', icon: 'layers', roles: [...FULL_ACCESS, ROLES.PROCUREMENT_OFFICER, ROLES.STORE_KEEPER, ROLES.SALES_STAFF, ROLES.COMPATIBILITY_MANAGER] },
  { path: '/vehicles', label: 'Vehicles', icon: 'car', roles: [...FULL_ACCESS, ROLES.STORE_KEEPER, ROLES.SALES_STAFF, ROLES.COMPATIBILITY_MANAGER] },
  { path: '/compatibility', label: 'Compatibility', icon: 'link', roles: [...FULL_ACCESS, ROLES.COMPATIBILITY_MANAGER] },
  { path: '/purchase-orders', label: 'Purchase Orders', icon: 'bag', roles: [...FULL_ACCESS, ROLES.PROCUREMENT_OFFICER] },
  { path: '/suppliers', label: 'Suppliers', icon: 'truck', roles: [...FULL_ACCESS, ROLES.PROCUREMENT_OFFICER] },
  { path: '/sales', label: 'Sales', icon: 'cart', roles: [...FULL_ACCESS, ROLES.SALES_STAFF] },
  { path: '/reports', label: 'Sales Reports', icon: 'chart', roles: FULL_ACCESS },
  { path: '/settings', label: 'Settings', icon: 'gear', roles: Object.values(ROLES) },
]

export const ROLE_OPTIONS = [
  { value: 'ROLE_SYSTEM_ADMINISTRATOR', label: 'System Administrator' },
  { value: 'ROLE_SHOP_OWNER', label: 'Shop Owner' },
  { value: 'ROLE_STORE_MANAGER', label: 'Store Manager' },
  { value: 'ROLE_PROCUREMENT_OFFICER', label: 'Procurement Officer' },
  { value: 'ROLE_STORE_KEEPER', label: 'Store Keeper' },
  { value: 'ROLE_SALES_STAFF', label: 'Sales Staff' },
  { value: 'ROLE_COMPATIBILITY_MANAGER', label: 'Compatibility Manager' },
]

export function normalizeRole(role) {
  if (!role) return ''
  return role.startsWith('ROLE_') ? role : `ROLE_${role}`
}

export function hasAccess(userRole, allowedRoles) {
  return allowedRoles.includes(normalizeRole(userRole))
}

export function roleLabel(role) {
  const match = ROLE_OPTIONS.find((item) => item.value === normalizeRole(role))
  return match ? match.label : role || 'Unknown'
}
