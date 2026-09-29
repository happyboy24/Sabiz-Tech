// Initial mock fallback data for rich interactive experience when backend DB is fresh/empty
export const INITIAL_PRODUCTS = [
  { id: 1, name: 'Sabi POS Terminal v2', sku: 'SKU-POS-001', categoryName: 'Hardware', price: 120000, costPrice: 85000, quantity: 18, reorderLevel: 5, unit: 'pcs', description: 'Smart Android POS Terminal with Thermal Printer' },
  { id: 2, name: 'Receipt Paper Rolls (58mm)', sku: 'SKU-PAP-058', categoryName: 'Supplies', price: 3500, costPrice: 2000, quantity: 150, reorderLevel: 30, unit: 'box', description: '58mm thermal paper roll pack of 10' },
  { id: 3, name: 'Wireless Barcode Scanner', sku: 'SKU-SCN-009', categoryName: 'Hardware', price: 28000, costPrice: 19000, quantity: 4, reorderLevel: 8, unit: 'pcs', description: '2.4G Handheld Bluetooth Barcode Reader' },
  { id: 4, name: 'Heavy Duty Cash Drawer', sku: 'SKU-CDR-410', categoryName: 'Hardware', price: 45000, costPrice: 32000, quantity: 12, reorderLevel: 3, unit: 'pcs', description: '5 Bill 8 Coin Electronic Cash Register Drawer' },
  { id: 5, name: 'Sabibiz Enterprise Software Key', sku: 'SKU-SFT-ENT', categoryName: 'Software', price: 65000, costPrice: 0, quantity: 999, reorderLevel: 10, unit: 'license', description: '1 Year Full Access License Key' }
];

export const INITIAL_CATEGORIES = [
  { id: 1, name: 'Hardware', description: 'POS Terminals, Printers, Scanners, and Drawers' },
  { id: 2, name: 'Supplies', description: 'Paper rolls, ink, labels, and packaging' },
  { id: 3, name: 'Software', description: 'Subscriptions, licenses, and add-on modules' }
];

export const INITIAL_CUSTOMERS = [
  { id: 1, name: 'Alhaji Musa Enterprises', email: 'musa@enterprises.ng', phone: '+234 803 123 4567', address: 'Kano Central Market', balance: 45000 },
  { id: 2, name: 'Grace Supermarket Ltd', email: 'orders@gracesupermarket.com', phone: '+234 812 987 6543', address: 'Victoria Island, Lagos', balance: 0 },
  { id: 3, name: 'Chidi & Sons Trading', email: 'chidi.sons@yahoo.com', phone: '+234 705 444 3322', address: 'Main Market, Onitsha', balance: 120000 }
];

export const INITIAL_SUPPLIERS = [
  { id: 1, name: 'Apex Tech Imports', email: 'sales@apextech.com', phone: '+234 802 000 1122', contactPerson: 'David Chen', address: 'Computer Village, Ikeja' },
  { id: 2, name: 'PaperCraft Industries', email: 'info@papercraft.ng', phone: '+234 809 333 4455', contactPerson: 'Fatima Bello', address: 'Idu Industrial Layout, Abuja' }
];

export const INITIAL_SALES = [
  { id: 101, saleNumber: 'SAL-202609-001', customerName: 'Grace Supermarket Ltd', totalAmount: 148000, paidAmount: 148000, status: 'COMPLETED', createdAt: '2026-09-28T14:30:00Z', itemsCount: 2 },
  { id: 102, saleNumber: 'SAL-202609-002', customerName: 'Alhaji Musa Enterprises', totalAmount: 165000, paidAmount: 120000, status: 'PARTIAL', createdAt: '2026-09-29T09:15:00Z', itemsCount: 3 },
  { id: 103, saleNumber: 'SAL-202609-003', customerName: 'Chidi & Sons Trading', totalAmount: 240000, paidAmount: 120000, status: 'PARTIAL', createdAt: '2026-09-29T10:00:00Z', itemsCount: 2 }
];

export const INITIAL_EXPENSES = [
  { id: 1, title: 'Store Generator Fuel', category: 'Utilities', amount: 35000, expenseDate: '2026-09-27', description: 'Diesel supply for week' },
  { id: 2, title: 'Internet Fiber Subscription', category: 'Operations', amount: 25000, expenseDate: '2026-09-25', description: 'Monthly fiber broadband' },
  { id: 3, title: 'Store Cleaner Salary', category: 'Payroll', amount: 40000, expenseDate: '2026-09-28', description: 'Monthly stipend' }
];

export const INITIAL_DEBTS = [
  { id: 1, customerName: 'Chidi & Sons Trading', amount: 120000, paidAmount: 0, dueDate: '2026-10-15', status: 'PENDING', note: 'Balance for POS Terminal purchase' },
  { id: 2, customerName: 'Alhaji Musa Enterprises', amount: 45000, paidAmount: 0, dueDate: '2026-10-05', status: 'PENDING', note: 'Outstanding from sale SAL-202609-002' }
];
