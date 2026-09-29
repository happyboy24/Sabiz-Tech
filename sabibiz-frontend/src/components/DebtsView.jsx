import React, { useState } from 'react';
import { ArrowLeftRight, Plus, Search, CheckCircle, Clock } from 'lucide-react';

export const DebtsView = ({ debts, onAddDebt, onSettleDebt }) => {
  const [search, setSearch] = useState('');
  const [showModal, setShowModal] = useState(false);

  const [customerName, setCustomerName] = useState('');
  const [amount, setAmount] = useState('');
  const [dueDate, setDueDate] = useState('');
  const [note, setNote] = useState('');

  const filtered = debts.filter((d) =>
    d.customerName.toLowerCase().includes(search.toLowerCase())
  );

  const totalOutstanding = debts.reduce(
    (sum, d) => sum + ((d.amount || 0) - (d.paidAmount || 0)),
    0
  );

  const handleSubmit = (e) => {
    e.preventDefault();
    onAddDebt({
      id: Date.now(),
      customerName,
      amount: parseFloat(amount) || 0,
      paidAmount: 0,
      dueDate: dueDate || '2026-10-31',
      status: 'PENDING',
      note,
    });
    setShowModal(false);
    setCustomerName('');
    setAmount('');
    setNote('');
  };

  return (
    <div className="space-y-6">
      {/* Metrics Banner & Search */}
      <div className="flex flex-col sm:flex-row items-center justify-between gap-4 bg-slate-900 border border-slate-800 p-4 rounded-2xl">
        <div className="flex items-center space-x-4">
          <div className="w-10 h-10 rounded-xl bg-purple-500/10 text-purple-400 flex items-center justify-center border border-purple-500/20">
            <ArrowLeftRight className="w-6 h-6" />
          </div>
          <div>
            <h3 className="font-bold text-white text-base">Total Outstanding Receivables</h3>
            <p className="text-sm font-extrabold text-purple-400">₦{totalOutstanding.toLocaleString()}</p>
          </div>
        </div>

        <div className="flex items-center space-x-3 w-full sm:w-auto">
          <div className="relative flex-1 sm:w-64">
            <Search className="w-4 h-4 text-slate-400 absolute left-3 top-3" />
            <input
              type="text"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="Search debts by customer..."
              className="w-full pl-9 pr-4 py-2 bg-slate-950 border border-slate-800 rounded-xl text-xs text-white placeholder-slate-500 focus:outline-none focus:border-indigo-500"
            />
          </div>

          <button
            onClick={() => setShowModal(true)}
            className="px-4 py-2 bg-indigo-600 hover:bg-indigo-500 text-white font-medium text-xs rounded-xl shadow-lg shadow-indigo-600/30 transition-all flex items-center space-x-2 shrink-0"
          >
            <Plus className="w-4 h-4" />
            <span>Record Debt</span>
          </button>
        </div>
      </div>

      {/* Debts Table */}
      <div className="bg-slate-900 border border-slate-800 rounded-2xl overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left text-xs text-slate-300">
            <thead className="bg-slate-950 uppercase text-[10px] tracking-wider text-slate-400 font-semibold border-b border-slate-800">
              <tr>
                <th className="py-3.5 px-4">Customer Name</th>
                <th className="py-3.5 px-4">Total Owed</th>
                <th className="py-3.5 px-4">Amount Paid</th>
                <th className="py-3.5 px-4">Balance Remaining</th>
                <th className="py-3.5 px-4">Due Date</th>
                <th className="py-3.5 px-4">Status</th>
                <th className="py-3.5 px-4 text-right">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-800/60">
              {filtered.map((d) => {
                const balance = d.amount - d.paidAmount;
                const isPaid = balance <= 0;
                return (
                  <tr key={d.id} className="hover:bg-slate-800/40 transition-colors">
                    <td className="py-3.5 px-4 font-semibold text-white">
                      {d.customerName}
                      {d.note && <div className="text-[11px] text-slate-500">{d.note}</div>}
                    </td>
                    <td className="py-3.5 px-4 font-bold text-slate-200">₦{d.amount?.toLocaleString()}</td>
                    <td className="py-3.5 px-4 text-emerald-400">₦{d.paidAmount?.toLocaleString()}</td>
                    <td className="py-3.5 px-4 font-extrabold text-amber-400">₦{balance.toLocaleString()}</td>
                    <td className="py-3.5 px-4 text-slate-400">{d.dueDate}</td>
                    <td className="py-3.5 px-4">
                      <span
                        className={`px-2 py-0.5 rounded-full text-[10px] font-bold ${
                          isPaid
                            ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                            : 'bg-amber-500/10 text-amber-400 border border-amber-500/20'
                        }`}
                      >
                        {isPaid ? 'SETTLED' : 'PENDING'}
                      </span>
                    </td>
                    <td className="py-3.5 px-4 text-right">
                      {!isPaid && (
                        <button
                          onClick={() => onSettleDebt(d.id, balance)}
                          className="px-2.5 py-1 bg-emerald-600/20 text-emerald-400 hover:bg-emerald-600/30 rounded-lg text-[11px] font-bold border border-emerald-500/30 transition-colors"
                        >
                          Mark Paid
                        </button>
                      )}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>

      {/* Modal Dialog */}
      {showModal && (
        <div className="fixed inset-0 z-50 bg-slate-950/80 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-slate-900 border border-slate-800 w-full max-w-md rounded-2xl p-6 shadow-2xl space-y-4">
            <h3 className="text-lg font-bold text-white">Record Customer Debt</h3>
            <form onSubmit={handleSubmit} className="space-y-3">
              <div>
                <label className="block text-xs font-medium text-slate-400 mb-1">Customer Name</label>
                <input
                  type="text"
                  required
                  value={customerName}
                  onChange={(e) => setCustomerName(e.target.value)}
                  placeholder="e.g. Alhaji Musa Enterprises"
                  className="w-full px-3 py-2 bg-slate-950 border border-slate-800 rounded-xl text-xs text-white focus:outline-none focus:border-indigo-500"
                />
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="block text-xs font-medium text-slate-400 mb-1">Debt Amount (₦)</label>
                  <input
                    type="number"
                    required
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    placeholder="0.00"
                    className="w-full px-3 py-2 bg-slate-950 border border-slate-800 rounded-xl text-xs text-white focus:outline-none focus:border-indigo-500"
                  />
                </div>
                <div>
                  <label className="block text-xs font-medium text-slate-400 mb-1">Due Date</label>
                  <input
                    type="date"
                    required
                    value={dueDate}
                    onChange={(e) => setDueDate(e.target.value)}
                    className="w-full px-3 py-2 bg-slate-950 border border-slate-800 rounded-xl text-xs text-white focus:outline-none focus:border-indigo-500"
                  />
                </div>
              </div>

              <div>
                <label className="block text-xs font-medium text-slate-400 mb-1">Note / Reference</label>
                <textarea
                  rows={2}
                  value={note}
                  onChange={(e) => setNote(e.target.value)}
                  placeholder="Reason for debt..."
                  className="w-full px-3 py-2 bg-slate-950 border border-slate-800 rounded-xl text-xs text-white focus:outline-none focus:border-indigo-500"
                />
              </div>

              <div className="flex justify-end space-x-3 pt-3">
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="px-4 py-2 bg-slate-800 text-slate-300 text-xs font-semibold rounded-xl hover:bg-slate-700"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-indigo-600 text-white text-xs font-semibold rounded-xl hover:bg-indigo-500 shadow-md"
                >
                  Save Debt
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
