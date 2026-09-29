import React from 'react';
import { Receipt, Search, Download, CheckCircle, Clock } from 'lucide-react';

export const SalesView = ({ sales }) => {
  return (
    <div className="space-y-6">
      <div className="bg-slate-900 border border-slate-800 rounded-2xl p-5">
        <div className="flex items-center justify-between mb-4">
          <div>
            <h3 className="font-bold text-white text-base">Completed & Active Sales Log</h3>
            <p className="text-xs text-slate-400">All historical POS transactions</p>
          </div>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full text-left text-xs text-slate-300">
            <thead className="bg-slate-950 uppercase text-[10px] tracking-wider text-slate-400 font-semibold border-b border-slate-800">
              <tr>
                <th className="py-3.5 px-4">Receipt #</th>
                <th className="py-3.5 px-4">Customer Name</th>
                <th className="py-3.5 px-4">Total Price</th>
                <th className="py-3.5 px-4">Paid</th>
                <th className="py-3.5 px-4">Payment Method</th>
                <th className="py-3.5 px-4">Status</th>
                <th className="py-3.5 px-4">Date</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-800/60">
              {sales.map((sale) => (
                <tr key={sale.id} className="hover:bg-slate-800/40 transition-colors">
                  <td className="py-3.5 px-4 font-mono font-semibold text-indigo-300">{sale.saleNumber}</td>
                  <td className="py-3.5 px-4 font-medium text-slate-200">{sale.customerName || 'Walk-in Customer'}</td>
                  <td className="py-3.5 px-4 font-bold text-white">₦{sale.totalAmount?.toLocaleString()}</td>
                  <td className="py-3.5 px-4 text-emerald-400">₦{sale.paidAmount?.toLocaleString()}</td>
                  <td className="py-3.5 px-4">
                    <span className="px-2 py-0.5 rounded bg-slate-800 text-slate-300 font-mono text-[10px]">
                      {sale.paymentMethod || 'CASH'}
                    </span>
                  </td>
                  <td className="py-3.5 px-4">
                    <span className={`px-2.5 py-1 rounded-full text-[10px] font-bold ${
                      sale.status === 'COMPLETED'
                        ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                        : 'bg-amber-500/10 text-amber-400 border border-amber-500/20'
                    }`}>
                      {sale.status}
                    </span>
                  </td>
                  <td className="py-3.5 px-4 text-slate-400">
                    {new Date(sale.createdAt || Date.now()).toLocaleString()}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};
