import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createGrado } from '../services/grado.service';
import { ArrowLeft, Save, GraduationCap } from 'lucide-react';

const GradoCreate: React.FC = () => {
  const [grado, setGrado] = useState({
    codigo: '',
    titulo: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setGrado(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      await createGrado(grado);
      navigate('/grados');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear el grado. Verifique si el código ya existe.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-4 md:p-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex items-center gap-4 mb-8">
          <button 
            onClick={() => navigate('/grados')}
            className="p-2 hover:bg-gray-200 rounded-full transition-colors"
          >
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-3xl font-bold text-gray-800">Añadir Nuevo Grado</h1>
        </div>

        <div className="bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
          <div className="p-6 bg-blue-600 text-white flex items-center gap-3">
            < GraduationCap size={24} />
            <h2 className="text-xl font-semibold">Datos del Grado</h2>
          </div>

          <form onSubmit={handleSubmit} className="p-6 space-y-6">
            {error && (
              <div className="bg-red-50 text-red-600 p-4 rounded-lg border border-red-100 text-sm">
                {error}
              </div>
            )}

            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Código del Grado</label>
                <input
                  type="text"
                  name="codigo"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                  value={grado.codigo}
                  onChange={handleChange}
                  placeholder="Ej: GII, GADE..."
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Título del Grado</label>
                <input
                  type="text"
                  name="titulo"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                  value={grado.titulo}
                  onChange={handleChange}
                  placeholder="Ej: Grado en Ingeniería Informática"
                />
              </div>
            </div>

            <div className="flex justify-end pt-4">
              <button
                type="submit"
                disabled={loading}
                className="flex items-center gap-2 bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors shadow-md disabled:bg-blue-400"
              >
                <Save size={20} />
                <span>{loading ? 'Guardando...' : 'Guardar Grado'}</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default GradoCreate;
