import React, { useState } from 'react';
import { Tags, Plus, Trash2, Folder } from 'lucide-react';

export const CategoriesView = ({ categories, onAddCategory }) => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name.trim()) return;
    onAddCategory({ id: Date.now(), name, description });
    setName('');
    setDescription('');
  };

  return (
    <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
      {/* Category Creation Form */}
      <div className="bg-slate-900 border border-slate-800 rounded-2xl p-5">
        <div className="flex items-center space-x-2 mb-4">
          <Tags className="w-5 h-5 text-indigo-400" />
          <h3 className="font-bold text-white text-base">New Category</h3>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">
              Category Name
            </label>
            <input
              type="text"
              required
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="e.g. Electronics, Groceries"
              className="w-full px-3.5 py-2.5 bg-slate-950 border border-slate-800 rounded-xl text-xs text-white placeholder-slate-500 focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">
              Description
            </label>
            <textarea
              rows={3}
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Brief description of products in this category..."
              className="w-full px-3.5 py-2.5 bg-slate-950 border border-slate-800 rounded-xl text-xs text-white placeholder-slate-500 focus:outline-none focus:border-indigo-500"
            />
          </div>

          <button
            type="submit"
            className="w-full py-2.5 bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-xs rounded-xl shadow-lg shadow-indigo-600/30 transition-all flex items-center justify-center space-x-2"
          >
            <Plus className="w-4 h-4" />
            <span>Create Category</span>
          </button>
        </form>
      </div>

      {/* Category Grid Cards */}
      <div className="lg:col-span-2 grid grid-cols-1 sm:grid-cols-2 gap-4">
        {categories.map((cat) => (
          <div key={cat.id} className="p-5 rounded-2xl bg-slate-900 border border-slate-800 flex flex-col justify-between">
            <div>
              <div className="w-9 h-9 rounded-xl bg-indigo-500/10 text-indigo-400 flex items-center justify-center border border-indigo-500/20 mb-3">
                <Folder className="w-5 h-5" />
              </div>
              <h4 className="font-bold text-white text-sm">{cat.name}</h4>
              <p className="text-xs text-slate-400 mt-1">{cat.description || 'No description provided.'}</p>
            </div>
            <div className="mt-4 pt-3 border-t border-slate-800/80 flex items-center justify-between text-xs text-slate-500">
              <span>ID: #{cat.id}</span>
              <span className="text-indigo-400 font-semibold">Active Tag</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
