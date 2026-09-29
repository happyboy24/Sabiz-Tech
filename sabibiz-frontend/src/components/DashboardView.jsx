import React from 'react';
import { 
  DollarSign, 
  ShoppingBag, 
  PackageX, 
  ArrowUpRight, 
  ArrowDownRight, 
  CreditCard, 
  TrendingUp,
  AlertTriangle,
  Clock,
  Plus
} from 'lucide-react';

export const DashboardView = ({ products, sales, expenses, debts, setActiveTab }) => {
  // Metric Calculations
  const totalRevenue = sales.reduce((sum, s) => sum + (s.totalAmount || 0), 0);
  const totalExpenses = expenses.reduce((sum, e) => sum + (e.amount || 0), 0);
  const netProfit = totalRevenue - totalExpenses;
  const lowStockCount = products.filter(p => p.quantity <= (p.reorderLevel || 5)).length;
  const pendingDebts = debts.reduce((sum, d) => sum + ((d.amount || 0) - (d.paidAmount || 0)), 0);

  return (
    <div className="space-y-6">
      {/* Top Banner */}
      <div className="p-6 rounded-2xl bg-gradient-to-r from-indigo-900/60 via-slate-900 to-purple-900/40 border border-indigo-500/20 flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
        <div>
          <h2 className="text-2xl font-bold text-white tracking-tight">Welcome to Sabibiz OS 🚀</h2>
          <p className="text-sm text-slate-300 mt-1">Here is an overview of your business performance today.</p>
        </div>
        <div className="flex items-center space-x-3">
          <button 
            onClick={() => setActiveTab('pos')}
            className="px-4 py-2.5 bg-indigo-600 hover:bg-indigo-500 text-white font-medium text-xs rounded-xl shadow-lg shadow-indigo-600/30 transition-all flex items-center space-x-2"
          >
            <Plus className="w-4 h-4" />
            <span>New POS Sale</span>
          </button>
          <button 
            onClick={() => setActiveTab('products')}
            className="px-4 py-2.5 bg-slate-800 hover:bg-slate-700 text-slate-200 font-medium text-xs rounded-xl border border-slate-700 transition-colors"
          >
            Add Product
          </button>
        </div>
      </div>

      {/* KPI Cards Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        {/* Total Revenue */}
        <div className="p-5 rounded-2xl bg-slate-900 border border-slate-800 flex flex-col justify-between">
          <div className="flex items-center justify-between">
            <span className="text-xs font-semibold uppercase tracking-wider text-slate-400">Total Sales Revenue</span>
            <div className="w-9 h-9 rounded-xl bg-emerald-500/10 text-emerald-400 flex items-center justify-center border border-emerald-500/20">
              <DollarSign className="w-5 h-5" />
            </div>
          </div>
          <div className="mt-4">
            <h3 className="text-2xl font-extrabold text-white">₦{totalRevenue.toLocaleString()}</h3>
            <div className="flex items-center space-x-1 text-xs text-emerald-400 mt-1">
              <ArrowUpRight className="w-3.5 h-3.5" />
              <span>+14.2% from last week</span>
            </div>
          </div>
        </div>

        {/* Net Profit */}
        <div className="p-5 rounded-2xl bg-slate-900 border border-slate-800 flex flex-col justify-between">
          <div className="flex items-center justify-between">
            <span className="text-xs font-semibold uppercase tracking-wider text-slate-400">Net Profit</span>
            <div className="w-9 h-9 rounded-xl bg-indigo-500/10 text-indigo-400 flex items-center justify-center border border-indigo-500/20">
              <TrendingUp className="w-5 h-5" />
            </div>
          </div>
          <div className="mt-4">
            <h3 className={`text-2xl font-extrabold ${netProfit >= 0 ? 'text-white' : 'text-rose-400'}`}>
              ₦{netProfit.toLocaleString()}
            </h3>
            <span className="text-xs text-slate-400 mt-1 block">After ₦{totalExpenses.toLocaleString()} expenses</span>
          </div>
        </div>

        {/* Low Stock Warning */}
        <div className="p-5 rounded-2xl bg-slate-900 border border-slate-800 flex flex-col justify-between">
          <div className="flex items-center justify-between">
            <span className="text-xs font-semibold uppercase tracking-wider text-slate-400">Low Stock Alerts</span>
            <div className="w-9 h-9 rounded-xl bg-amber-500/10 text-amber-400 flex items-center justify-center border border-amber-500/20">
              <PackageX className="w-5 h-5" />
            </div>
          </div>
          <div className="mt-4">
            <h3 className="text-2xl font-extrabold text-white">{lowStockCount} Items</h3>
            <span className="text-xs text-amber-400 mt-1 block font-medium">Needs reordering soon</span>
          </div>
        </div>

        {/* Receivables/Debts */}
        <div className="p-5 rounded-2xl bg-slate-900 border border-slate-800 flex flex-col justify-between">
          <div className="flex items-center justify-between">
            <span className="text-xs font-semibold uppercase tracking-wider text-slate-400">Pending Receivables</span>
            <div className="w-9 h-9 rounded-xl bg-purple-500/10 text-purple-400 flex items-center justify-center border border-purple-500/20">
              <CreditCard className="w-5 h-5" />
            </div>
          </div>
          <div className="mt-4">
            <h3 className="text-2xl font-extrabold text-white">₦{pendingDebts.toLocaleString()}</h3>
            <span className="text-xs text-slate-400 mt-1 block">Outstanding customer debt</span>
          </div>
        </div>
      </div>

      {/* Main Content Grid: Recent Transactions & Low Stock Table */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Recent Sales History (2 Cols) */}
        <div className="lg:col-span-2 bg-slate-900 border border-slate-800 rounded-2xl p-5">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h3 className="font-bold text-white text-base">Recent Sales Transactions</h3>
              <p className="text-xs text-slate-400">Latest completed and pending orders</p>
            </div>
            <button 
              onClick={() => setActiveTab('sales')}
              className="text-xs text-indigo-400 font-semibold hover:underline"
            >
              View All
            </button>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs text-slate-300">
              <thead className="bg-slate-950/60 uppercase text-[10px] tracking-wider text-slate-400 font-semibold border-b border-slate-800">
                <tr>
                  <th className="py-3 px-4">Receipt #</th>
                  <th className="py-3 px-4">Customer</th>
                  <th className="py-3 px-4">Amount</th>
                  <th className="py-3 px-4">Status</th>
                  <th className="py-3 px-4">Date</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-800/60">
                {sales.slice(0, 5).map((sale) => (
                  <tr key={sale.id} className="hover:bg-slate-800/40 transition-colors">
                    <td className="py-3 px-4 font-mono font-medium text-indigo-300">{sale.saleNumber}</td>
                    <td className="py-3 px-4 font-medium text-slate-200">{sale.customerName || 'Walk-in Customer'}</td>
                    <td className="py-3 px-4 font-bold text-white">₦{sale.totalAmount?.toLocaleString()}</td>
                    <td className="py-3 px-4">
                      <span className={`px-2 py-0.5 rounded-full text-[10px] font-bold ${
                        sale.status === 'COMPLETED' 
                          ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                          : 'bg-amber-500/10 text-amber-400 border border-amber-500/20'
                      }`}>
                        {sale.status}
                      </span>
                    </td>
                    <td className="py-3 px-4 text-slate-400">
                      {new Date(sale.createdAt || Date.now()).toLocaleDateString()}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        {/* Low Inventory Panel (1 Col) */}
        <div className="bg-slate-900 border border-slate-800 rounded-2xl p-5 flex flex-col justify-between">
          <div>
            <div className="flex items-center space-x-2 mb-4">
              <AlertTriangle className="w-4 h-4 text-amber-400" />
              <h3 className="font-bold text-white text-base">Low Stock Attention</h3>
            </div>

            <div className="space-y-3">
              {products.filter(p => p.quantity <= (p.reorderLevel || 5)).map((product) => (
                <div key={product.id} className="p-3 rounded-xl bg-slate-950 border border-slate-800 flex items-center justify-between">
                  <div>
                    <h4 className="text-xs font-semibold text-slate-200">{product.name}</h4>
                    <p className="text-[11px] text-slate-400">Category: {product.categoryName || 'General'}</p>
                  </div>
                  <div className="text-right">
                    <span className="text-xs font-bold text-rose-400 block">{product.quantity} {product.unit || 'pcs'} left</span>
                    <span className="text-[10px] text-slate-500">Reorder at {product.reorderLevel || 5}</span>
                  </div>
                </div>
              ))}
              {products.filter(p => p.quantity <= (p.reorderLevel || 5)).length === 0 && (
                <p className="text-xs text-slate-400 py-4 text-center">All inventory stock levels are healthy! 🎉</p>
              )}
            </div>
          </div>

          <button
            onClick={() => setActiveTab('products')}
            className="w-full mt-4 py-2 bg-slate-800 hover:bg-slate-700 text-indigo-400 text-xs font-semibold rounded-xl border border-slate-700 transition-colors"
          >
            Manage Inventory
          </button>
        </div>
      </div>
    </div>
  );
};
