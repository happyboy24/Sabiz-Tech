import React from 'react';
import { Bell, Search, Store, Server, ShieldCheck } from 'lucide-react';

export const Header = ({ activeTab, user }) => {
  const titleMap = {
    dashboard: 'Executive Dashboard',
    pos: 'Point of Sale (POS)',
    products: 'Inventory Management',
    categories: 'Product Categories',
    sales: 'Sales Transaction History',
    customers: 'Customer Directory',
    suppliers: 'Supplier Directory',
    expenses: 'Expense Management',
    debts: 'Debts & Receivables',
    business: 'Business Settings',
  };

  return (
    <header className="h-16 bg-slate-900/80 backdrop-blur-md border-b border-slate-800 px-6 flex items-center justify-between sticky top-0 z-10">
      <div>
        <h2 className="text-xl font-bold text-white tracking-tight">{titleMap[activeTab] || 'Sabibiz'}</h2>
        <p className="text-xs text-slate-400">Manage real-time operations and financials</p>
      </div>

      <div className="flex items-center space-x-4">
        {/* Quick Search */}
        <div className="relative hidden sm:block">
          <Search className="w-4 h-4 text-slate-400 absolute left-3 top-2.5" />
          <input
            type="text"
            placeholder="Search transactions, items, customers..."
            className="w-64 pl-9 pr-4 py-1.5 bg-slate-950 border border-slate-800 rounded-lg text-xs text-slate-200 placeholder-slate-500 focus:outline-none focus:border-indigo-500 transition-colors"
          />
        </div>

        {/* Backend Status Pill */}
        <div className="flex items-center space-x-1.5 px-3 py-1 rounded-full bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 text-xs font-medium">
          <Server className="w-3.5 h-3.5" />
          <span>Spring API Active</span>
        </div>

        {/* Notifications Button */}
        <button className="relative p-2 rounded-lg text-slate-400 hover:text-slate-200 hover:bg-slate-800 transition-colors">
          <Bell className="w-4 h-4" />
          <span className="absolute top-1.5 right-1.5 w-2 h-2 rounded-full bg-indigo-500"></span>
        </button>

        {/* Current Store Badge */}
        <div className="hidden md:flex items-center space-x-2 pl-2 border-l border-slate-800 text-slate-300 text-xs font-medium">
          <Store className="w-4 h-4 text-indigo-400" />
          <span>Main Branch (Lagos)</span>
        </div>
      </div>
    </header>
  );
};
