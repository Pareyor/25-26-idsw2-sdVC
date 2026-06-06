import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, ChevronLeft, ChevronRight } from 'lucide-react';
import examenService from '../services/examen.service';
import * as alumnoService from '../services/alumno.service';
import * as gradoService from '../services/grado.service';
import './Listas.css';
import './Formularios.css';

const PAGE_SIZE = 10;

const ConfirmarAsignacion: React.FC = () => {
  const [borradores, setBorradores] = useState<any[]>([]);
  const [alumnosPorGrado, setAlumnosPorGrado] = useState<Record<number, any[]>>({});
  const [grados, setGrados] = useState<any[]>([]);
  const [searchTerm, setSearchTerm] = useState<Record<number, string>>({});
  const [currentPage, setCurrentPage] = useState<Record<number, number>>({});
  const [selectedAlumnos, setSelectedAlumnos] = useState<Set<number>>(new Set());
  const navigate = useNavigate();

  useEffect(() => {
    Promise.all([
      examenService.getBorradores(),
      gradoService.getGrados()
    ]).then(([borradoresRes, gradosRes]) => {
      const b = borradoresRes.data;
      setBorradores(b);
      setGrados(gradosRes.data);
      
      const gradoIds = Array.from(new Set(b.map((pl: any) => pl.gradoId))) as number[];
      
      gradoIds.forEach(id => {
        alumnoService.getAlumnosByGrado(id).then(res => {
          setAlumnosPorGrado(prev => ({ ...prev, [id]: res.data }));
          setCurrentPage(prev => ({ ...prev, [id]: 0 }));
        });
      });
    });
  }, []);

  const toggleAlumno = (id: number, gradoId: number) => {
    const borradoresGrado = borradores.filter(b => b.gradoId === gradoId).length;
    
    setSelectedAlumnos(prev => {
      const next = new Set(prev);
      const alumnosSeleccionadosEnGrado = alumnosPorGrado[gradoId].filter(a => next.has(a.id)).length;
      
      if (next.has(id)) {
        next.delete(id);
      } else if (alumnosSeleccionadosEnGrado < borradoresGrado) {
        next.add(id);
      } else {
        alert(`Límite alcanzado: Solo hay ${borradoresGrado} exámenes para este grado.`);
      }
      return next;
    });
  };

  const seleccionarMaximo = (gradoId: number) => {
    const borradoresGrado = borradores.filter(b => b.gradoId === gradoId).length;
    const alumnos = alumnosPorGrado[gradoId] || [];
    
    setSelectedAlumnos(prev => {
      const next = new Set(prev);
      alumnos.forEach(a => next.delete(a.id));
      alumnos.slice(0, borradoresGrado).forEach(a => next.add(a.id));
      return next;
    });
  };

  const handleConfirmar = async () => {
    if (selectedAlumnos.size === 0) {
      alert("Debes seleccionar al menos un alumno.");
      return;
    }
    try {
      await examenService.asignarExamenes(Array.from(selectedAlumnos));
      alert('Exámenes asignados correctamente');
      navigate('/dashboard');
    } catch (err) {
      alert('Error al asignar exámenes');
    }
  };

  return (
    <div className="form-container" style={{ maxWidth: '900px' }}>
      <div className="list-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h2>Confirmar Asignación</h2>
        <button className="btn btn-primary" onClick={handleConfirmar} disabled={selectedAlumnos.size === 0}>
          Confirmar Asignación ({selectedAlumnos.size})
        </button>
      </div>
      
      {Object.entries(alumnosPorGrado).map(([gradoId, alumnos]) => {
        const gId = parseInt(gradoId);
        const grado = grados.find(g => g.id === gId);
        const borradoresGrado = borradores.filter(b => b.gradoId === gId).length;
        const seleccionadosGrado = alumnos.filter(a => selectedAlumnos.has(a.id)).length;
        const busqueda = searchTerm[gId] || '';
        const alumnosFiltrados = alumnos.filter(a => 
            `${a.nombre} ${a.apellidos}`.toLowerCase().includes(busqueda.toLowerCase())
        );
        
        const page = currentPage[gId] || 0;
        const totalPages = Math.ceil(alumnosFiltrados.length / PAGE_SIZE);
        const paginatedAlumnos = alumnosFiltrados.slice(page * PAGE_SIZE, (page + 1) * PAGE_SIZE);

        return (
          <div key={gradoId} className="list-container" style={{ marginBottom: '2rem' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>{grado ? grado.titulo : `Grado ${gradoId}`} ({seleccionadosGrado}/{borradoresGrado} asignados)</h3>
              <button className="btn" onClick={() => seleccionarMaximo(gId)}>Seleccionar Máximo</button>
            </div>
            
            <div className="search-filter-wrapper" style={{ marginTop: '1rem' }}>
                <Search size={20} />
                <input
                    type="text"
                    className="search-filter-input"
                    placeholder="Buscar alumno..."
                    value={busqueda}
                    onChange={(e) => {
                        setSearchTerm({...searchTerm, [gId]: e.target.value});
                        setCurrentPage(prev => ({ ...prev, [gId]: 0 }));
                    }}
                />
            </div>

            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>Seleccionar</th>
                    <th>Nombre</th>
                    <th>DNI</th>
                  </tr>
                </thead>
                <tbody>
                  {paginatedAlumnos.map((a: any) => (
                    <tr key={a.id} onClick={() => toggleAlumno(a.id, gId)} style={{ cursor: 'pointer' }}>
                      <td>
                        <input type="checkbox" checked={selectedAlumnos.has(a.id)} onChange={() => {}} />
                      </td>
                      <td>{a.nombre} {a.apellidos}</td>
                      <td>{a.dni}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
              
              {totalPages > 1 && (
                <div className="pagination-controls" style={{ display: 'flex', justifyContent: 'center', gap: '1rem', marginTop: '1rem' }}>
                    <button className="btn" onClick={() => setCurrentPage(prev => ({...prev, [gId]: Math.max(0, page - 1)}))} disabled={page === 0}>
                        <ChevronLeft size={20} />
                    </button>
                    <span>Página {page + 1} de {totalPages}</span>
                    <button className="btn" onClick={() => setCurrentPage(prev => ({...prev, [gId]: Math.min(totalPages - 1, page + 1)}))} disabled={page === totalPages - 1}>
                        <ChevronRight size={20} />
                    </button>
                </div>
              )}
            </div>
          </div>
        );
      })}

      <div className="form-actions">
        <button className="btn btn-danger" onClick={() => navigate('/examenes/generar')}>Volver</button>
      </div>
    </div>
  );
};

export default ConfirmarAsignacion;
