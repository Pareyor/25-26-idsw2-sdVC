import React, { useEffect, useState } from 'react';
import { getPreguntas } from '../services/pregunta.service';
import type { Pregunta } from '../types/pregunta';
import { Search, Plus, Edit, Trash2, ArrowLeft, HelpCircle, Filter } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { normalizeString } from '../utils/searchUtils';

const PreguntaList: React.FC = () => {
  const [preguntas, setPreguntas] = useState<Pregunta[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchPreguntas();
  }, []);

  const fetchPreguntas = async () => {
    try {
      const response = await getPreguntas();
      setPreguntas(response.data);
      setLoading(false);
    } catch (err) {
      setError('Error al cargar las preguntas');
      setLoading(false);
    }
  };

  const filteredPreguntas = preguntas.filter(pregunta => {
    const term = normalizeString(searchTerm);
    return normalizeString(pregunta.enunciado).includes(term) ||
           normalizeString(pregunta.tema).includes(term) ||
           normalizeString(pregunta.dificultad).includes(term);
  });

  const getDificultadColor = (dificultad: string) => {
    switch (dificultad) {
      case 'FACIL': return 'bg-green-100 text-green-800';
      case 'MEDIO': return 'bg-yellow-100 text-yellow-800';
      case 'DIFICIL': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  if (loading) return <div className="p-8 text-center">Cargando preguntas...</div>;
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
                <HelpCircle className="text-blue-600" size={32} />
                <h1 className="text-3xl font-bold text-gray-800">Batería de Preguntas</h1>
            </div>
          </div>
          <button 
            onClick={() => navigate('/preguntas/nuevo')}
            className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors shadow-md"
          >
            <Plus size={20} />
            <span>Crear Pregunta</span>
          </button>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
          <div className="p-4 border-b border-gray-200 bg-gray-50 flex flex-wrap gap-4 items-center justify-between">
            <div className="relative max-w-md w-full">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Buscar por enunciado, tema o dificultad..."
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="flex items-center gap-2 text-gray-600">
                <Filter size={20} />
                <span className="text-sm font-medium">Filtros avanzados (próximamente)</span>
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full text-left">
              <thead className="bg-gray-50 text-gray-600 uppercase text-sm">
                <tr>
                  <th className="px-6 py-4 font-semibold">Enunciado</th>
                  <th className="px-6 py-4 font-semibold">Tema</th>
                  <th className="px-6 py-4 font-semibold">Dificultad</th>
                  <th className="px-6 py-4 font-semibold text-center">Acciones</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {filteredPreguntas.map((pregunta) => (
                  <tr key={pregunta.id} className="hover:bg-gray-50 transition-colors">
                    <td className="px-6 py-4 text-gray-900 max-w-md truncate" title={pregunta.enunciado}>
                        {pregunta.enunciado}
                    </td>
                    <td className="px-6 py-4">
                        <span className="px-2 py-1 bg-blue-50 text-blue-700 rounded text-xs font-bold">
                            {pregunta.tema}
                        </span>
                    </td>
                    <td className="px-6 py-4">
                      <span className={`px-3 py-1 rounded-full text-xs font-bold ${getDificultadColor(pregunta.dificultad)}`}>
                        {pregunta.dificultad}
                      </span>
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex justify-center gap-3">
                        <button 
                          onClick={() => navigate(`/preguntas/editar/${pregunta.id}`)}
                          className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors" 
                          title="Editar"
                        >
                          <Edit size={18} />
                        </button>
                        <button 
                          onClick={() => handleDelete(pregunta.id)}
                          className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors" 
                          title="Eliminar"
                        >
                          <Trash2 size={18} />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
                {filteredPreguntas.length === 0 && (
                  <tr>
                    <td colSpan={4} className="px-6 py-8 text-center text-gray-500">
                      No se encontraron preguntas que coincidan con la búsqueda.
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

export default PreguntaList;
n preguntas que coincidan con la búsqueda.
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

export default PreguntaList;
