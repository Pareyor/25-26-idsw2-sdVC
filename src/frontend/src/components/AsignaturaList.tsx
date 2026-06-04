import React, { useEffect, useState } from 'react';
import { getAsignaturas } from '../services/asignatura.service';
import type { Asignatura } from '../services/asignatura.service';
import { Search, Plus, Edit, Trash2, ArrowLeft, BookOpen } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { normalizeString } from '../utils/searchUtils';

const AsignaturaList: React.FC = () => {
  const [asignaturas, setAsignaturas] = useState<Asignatura[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchAsignaturas();
  }, []);

  const fetchAsignaturas = async () => {
    try {
      const response = await getAsignaturas();
      setAsignaturas(response.data);
      setLoading(false);
    } catch (err) {
      setError('Error al cargar las asignaturas');
      setLoading(false);
    }
  };

  const filteredAsignaturas = asignaturas.filter(asignatura => {
    const term = normalizeString(searchTerm);
    return normalizeString(asignatura.titulo).includes(term) ||
           normalizeString(asignatura.codigo).includes(term);
  });

  if (loading) return <div className="p-8 text-center">Cargando asignaturas...</div>;
  if (error) return <div className="p-8 text-center text-red-500">{error}</div>;

  return (
    <div className="min-h-screen bg-gray-50 p-4 md:p-8">
      <div className="max-w-6xl mx-auto">
        <div className="flex flex-col md:flex-row md:items-center justify-between mb-8 gap-4">
          <div className="flex items-center gap-4">
            <button 
              onClick={() => navigate('/dashboard')}
              className="p-2 hover:bg-gray-200 rounded-full transition-colors"
            >
              <ArrowLeft size={24} />
            </button>
            <div className="flex items-center gap-3">
                <BookOpen className="text-blue-600" size={32} />
                <h1 className="text-3xl font-bold text-gray-800">Gestión de Asignaturas</h1>
            </div>
          </div>
          <button 
            onClick={() => navigate('/asignaturas/nuevo')}
            className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors shadow-md"
          >
            <Plus size={20} />
            <span>Añadir Asignatura</span>
          </button>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
          <div className="p-4 border-b border-gray-200 bg-gray-50">
            <div className="relative max-w-md">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Buscar por código o título..."
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full text-left">
              <thead className="bg-gray-50 text-gray-600 uppercase text-sm">
                <tr>
                  <th className="px-6 py-4 font-semibold">Código</th>
                  <th className="px-6 py-4 font-semibold">Título</th>
                  <th className="px-6 py-4 font-semibold">Curso</th>
                  <th className="px-6 py-4 font-semibold text-center">Acciones</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {filteredAsignaturas.map((asignatura) => (
                  <tr key={asignatura.id} className="hover:bg-gray-50 transition-colors">
                    <td className="px-6 py-4 font-medium text-gray-900">{asignatura.codigo}</td>
                    <td className="px-6 py-4 text-gray-700">{asignatura.titulo}</td>
                    <td className="px-6 py-4 text-gray-700">{asignatura.cursoAcademico}</td>
                    <td className="px-6 py-4">
                      <div className="flex justify-center gap-3">
                        <button className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors" title="Editar">
                          <Edit size={18} />
                        </button>
                        <button className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors" title="Eliminar">
                          <Trash2 size={18} />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
                {filteredAsignaturas.length === 0 && (
                  <tr>
                    <td colSpan={4} className="px-6 py-8 text-center text-gray-500">
                      No se encontraron asignaturas que coincidan con la búsqueda.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AsignaturaList;
