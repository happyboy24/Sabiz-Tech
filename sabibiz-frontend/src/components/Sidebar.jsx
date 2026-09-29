import React from 'react';
import { 
  LayoutDashboard, 
  ShoppingCart, 
  Package, 
  Tags, 
  Users, 
  Truck, 
  Receipt, 
  Wallet, 
  ArrowLeftRight,
  Building,
  LogOut,
  ChevronRight,
  TrendingUp,
  ShieldCheck
} from 'lucide-react';

const navItems = [
  { id: 'dashboard', label: 'Dashboard', icon: LayoutDashboard },
  { id: 'pos', label: 'POS Terminal', icon: ShoppingCart, badge: 'Live' },
  { id: 'products', label: 'Inventory / Products', icon: Package },
  { id: 'categories', label: 'Categories', icon: Tags },
  { id: 'sales', label: 'Sales Records', icon: Receipt },
  { id: 'customers', label: 'Customers', icon: Users },
  { id: 'suppliers', label: 'Suppliers', icon: Truck },
  { id: 'expenses', label: 'Expenses', icon: Wallet },
  { id: 'debts', label: 'Debts & Receivables', icon: ArrowLeftRight },
  { id: 'business', label: 'Business Profile', icon: Building },
];

export const Sidebar = ({ activeTab, setActiveTab, user, onLogout }) => {
  return (
    <aside className="w-64 bg-slate-900 border-r border-slate-800 flex flex-col justify-between shrink-0 min-h-screen">
      <div>
        {/* Brand Logo */}
        <div className="p-5 border-b border-slate-800 flex items-center space-x-3">
          <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-indigo-600 via-indigo-500 to-purple-500 flex items-center justify-center shadow-lg shadow-indigo-500/20">
            <TrendingUp className="w-6 h-6 text-white" />
          </div>
          <div>
            <h1 className="font-bold text-lg text-white tracking-wide leading-none">Sabibiz</h1>
            <span className="text-xs font-medium text-indigo-400">Business OS v1.0</span>
          </div>
        </div>

        {/* Navigation */}
        <nav className="p-3 space-y-1">
          {navItems.map((item) => {
            const Icon = item.icon;
            const isActive = activeTab === item.id;
            return (
              <button
                key={item.id}
                onClick={() => setActiveTab(item.id)}
                className={`w-full flex items-center justify-between px-3.5 py-2.5 rounded-xl text-sm font-medium transition-all duration-150 ${
                  isActive
                    ? 'bg-indigo-600 text-white shadow-md shadow-indigo-600/30'
                    : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
                }`}
              >
                <div className="flex items-center space-x-3">
                  <Icon className={`w-4 h-4 ${isActive ? 'text-white' : 'text-slate-400'}`} />
                  <span>{item.label}</span>
                </div>
                {item.badge ? (
                  <span className="px-1.5 py-0.5 text-[10px] uppercase font-bold tracking-wider rounded bg-indigo-500/20 text-indigo-300 border border-indigo-400/30">
                    {item.badge}
                  </span>
                ) : (
                  isActive && <ChevronRight className="w-4 h-4 text-indigo-200 opacity-80" />
                )}
              </button>
            );
          })}
        </nav>
      </div>

      {/* User Info & Logout */}
      <div className="p-3 border-t border-slate-800">
        <div className="p-3 rounded-xl bg-slate-800/40 border border-slate-700/50 mb-2 flex items-center space-x-3">
          <div className="w-9 h-9 rounded-full bg-slate-700 flex items-center justify-center text-indigo-400 font-bold border border-indigo-500/30">
            {user?.username?.[0]?.toUpperCase() || 'U'}
          </div>
          <div className="flex-1 min-w-0">
            <p className="text-sm font-medium text-slate-200 truncate">{user?.username || 'Admin User'}</p>
            <p className="text-xs text-slate-400 flex items-center space-x-1">
              <ShieldCheck className="w-3 h-3 text-emerald-400 inline mr-1" />
              <span>{user?.role || 'Manager'}</span>
            </p>
          </div>
        </div>

        <button
          onClick={onLogout}
          className="w-full flex items-center justify-center space-x-2 px-3 py-2 rounded-xl text-xs font-semibold text-rose-400 hover:bg-rose-950/40 hover:text-rose-300 transition-colors border border-transparent hover:border-rose-900/50"
        >
          <LogOut className="w-4 h-4" />
          <span>Sign Out</span>
        </button>
      </div>
    </aside>
  );
};
